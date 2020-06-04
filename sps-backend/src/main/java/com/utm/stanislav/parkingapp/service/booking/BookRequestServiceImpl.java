package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.model.enums.BookRequestStatus;
import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.exceptions.SpaceFinderException;
import com.utm.stanislav.parkingapp.model.exceptions.StrategyResolverException;
import com.utm.stanislav.parkingapp.repository.BookRequestRepository;
import com.utm.stanislav.parkingapp.service.booking.strategy.BookingStrategy;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyHolder;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyResolver;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyType;
import com.utm.stanislav.parkingapp.service.spacefinder.strategy.SpaceFinderStrategy;
import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import com.utm.stanislav.parkingapp.web.dto.LevelDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotDto;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.service.parking.ParkingService;
import com.utm.stanislav.parkingapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookRequestServiceImpl implements BookRequestService {
    
    private final ApplicationContext context;
    private final ParkingService parkingService;
    private final UserService userService;
    private final BookingStrategySelectorMBean bookingStrategySelector;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    private final FunctionMessageService functionMessageService;
    private final SpaceFinderStrategyResolver spaceFinderStrategyResolver;
    private final BookRequestRepository bookRequestRepository;
    
    @Override
    @Transactional
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public ParkingLotDto book(ParkingDto parkingDTO, String username) throws BookingException {
        log.info("Booking a parking lot for user [{}]", username.toUpperCase());
        Parking parking = this.parkingService.getParkingById(parkingDTO.getId())
                .orElseThrow(() -> new BookingException(
                        "Parking with name " + parkingDTO.getName() + " could not be found!"));
        
        BookingStrategy bookingStrategy = fetchStrategyOrReturnDefault();
        ParkingLot bookedParkingLot = bookingStrategy.book(parking);
        log.info("Found suitable parking lot [{}] at level [{}]",
                bookedParkingLot.getLogicalId(), bookedParkingLot.getLevel().getLogicalId());
        
        User user = this.userService.getByUsername(username)
                .orElseThrow(() -> new BookingException(
                        "User requesting booking was not found!"));
        
        bookedParkingLot.setParkingStatus(ParkingStatus.BOOKED);
        bookedParkingLot.setBookedBy(user);
        user.setParkingLot(bookedParkingLot);
        log.info("Successfully finished booking a place for user [{}]", user.getUsername());
        
        String bridgeSessionId = bookedParkingLot.getRPiBridge().getSessionId();
        if (bridgeSessionId != null) {
            log.info("Sending blocking message to bridge with sessionId[{}]", bridgeSessionId);
            List<FunctionMessage> bookingMessageList = new ArrayList<>();
            bookingMessageList.add(functionMessageService.generateFor(bookedParkingLot, FunctionCode.BLOCK_LOT));
            brokerMessagingTemplate.convertAndSendToUser(bridgeSessionId,
                    "/messages/function",
                    bookingMessageList,
                    createHeaders(bridgeSessionId));
        }
        else {
            log.error("Could not send a blocking message to corresponding bridge!");
        }
        
    
        LevelDto levelDTO = new LevelDto();
        return new ParkingLotDto(bookedParkingLot.getLogicalId() ,levelDTO);
    }
    
    private BookingStrategy fetchStrategyOrReturnDefault() {
        String strategy = this.bookingStrategySelector.getBookingStrategy();
        BookingStrategy bookingStrategy;
        try {
            bookingStrategy = context.getBean(strategy, BookingStrategy.class);
        } catch (NoSuchBeanDefinitionException ex) {
            String defaultStrategy = this.bookingStrategySelector.getDefaultBookingStrategy();
            bookingStrategy = context.getBean(defaultStrategy, BookingStrategy.class);
        }
        return bookingStrategy;
    }
    
    
    @Override
    @Transactional
    @Retryable(
            value = {OptimisticLockException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 100)
    )
    public void createOne(ParkingDto parkingDTO, User user) throws BookingException {
        log.info("New booking request from user {} in parking {}", user.getUsername(), parkingDTO.getName());
        Parking parking = this.parkingService.getParkingById(parkingDTO.getId())
                                  .orElseThrow(() -> new BookingException(
                                          "Parking " + parkingDTO.getName() + " could not be found in the system!"));
    
        ParkingLot parkingLot = findParkingLot(parking);
        log.info("Found {} spot, booking it for user {}", parkingLot.getLogicalId(), user.getUsername());
    
        sendBlockRequestToPhysicalSpace(parkingLot);
        log.info("Successfully send a blocking message to {} parking lot", parkingLot.getLogicalId());
    
        BookRequest bookRequest = new BookRequest(
                parkingLot, user, 0, BookRequestStatus.AWAITING_CONFIRMATION
        );
        bookRequest = bookRequestRepository.save(bookRequest);
        log.info("Finished creating a book request for user {}", user.getUsername());
    
    }
    
    private ParkingLot findParkingLot(Parking parking) throws BookingException {
        SpaceFinderStrategyType parkingStrategyType = SpaceFinderStrategyHolder.INSTANCE.getStrategy(parking.getId());
        log.info("Current parking spaceFinderStrategy is {}", parkingStrategyType.getType());
        try {
            SpaceFinderStrategy spaceFinderStrategy
                    = spaceFinderStrategyResolver.resolve(parkingStrategyType);
            
            log.debug("Found an implementation for {} spaceFinderStrategyType", parkingStrategyType.getType());
            
            return spaceFinderStrategy.findOne(parking);
        }
        catch (StrategyResolverException ex) {
            log.error("Could not resolve a default strategy implementation. This should never happen!");
            throw new BookingException("Oops. Something went wrong. Please try again later.");
        }
        catch (SpaceFinderException ex) {
            log.info("No free parking lots found in {} parking", parking.getLogicalId());
            throw new BookingException(ex.getMessage());
        }
    }
    
    private void sendBlockRequestToPhysicalSpace(ParkingLot parkingLot) {
        String sessionId = parkingLot.getRPiBridge().getSessionId();
        log.info("Sending blocking message to bridge with sessionId[{}]", sessionId);
        List<FunctionMessage> bookingMessageList = new ArrayList<>();
        bookingMessageList.add(functionMessageService.generateFor(parkingLot, FunctionCode.BLOCK_LOT));
        brokerMessagingTemplate.convertAndSendToUser(sessionId,
                "/messages/function",
                bookingMessageList,
                createHeaders(sessionId));
    }
    
    private MessageHeaders createHeaders(String sessionId) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        stompHeaderAccessor.setSessionId(sessionId);
        stompHeaderAccessor.setLeaveMutable(Boolean.TRUE);
        return stompHeaderAccessor.getMessageHeaders();
    }
    
    @Override
    public Optional<BookRequest> getFor(ParkingLot parkingLot) {
        return bookRequestRepository.findByParkingLot(parkingLot);
    }
    
    @Override
    @Transactional
    public void confirmArrival(UUID bookRequestId, User user) throws BookingException {
        BookRequest bookRequest = bookRequestRepository.findById(bookRequestId)
                .orElseThrow(() -> new BookingException("Could not find book request with ID[" + bookRequestId + "]"));
        
        validateConfirmRequest(bookRequest, user);
        
        ParkingLot parkingLot = bookRequest.getParkingLot();
        if (parkingLot.getRPiBridge().getIsConnected()) {
            sendUnblockRequestToPhysicalSpace(parkingLot);
        }
    }
    
    private void validateConfirmRequest(BookRequest bookRequest, User user) throws BookingException {
        if (!bookRequest.getUser().getUsername().equals(user.getUsername())) {
            throw new BookingException("User " + user.getUsername() + " is not the one which issued current book request!");
        }
    
        if (bookRequest.getBookRequestStatus() != BookRequestStatus.CONFIRMED) {
            throw new BookingException("You cannot confirm arrival for this book request yet!");
        }
    }
    
    private void sendUnblockRequestToPhysicalSpace(ParkingLot parkingLot) {
        String sessionId = parkingLot.getRPiBridge().getSessionId();
        log.info("Sending blocking message to bridge with sessionId[{}]", sessionId);
        List<FunctionMessage> bookingMessageList = new ArrayList<>();
        bookingMessageList.add(functionMessageService.generateFor(parkingLot, FunctionCode.UNBLOCK_LOT));
        brokerMessagingTemplate.convertAndSendToUser(sessionId,
                "/messages/function",
                bookingMessageList,
                createHeaders(sessionId));
    }
    
    @Override
    public void delete(BookRequest bookRequest) {
        bookRequestRepository.delete(bookRequest);
    }
}
