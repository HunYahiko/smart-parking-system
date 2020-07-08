package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.fixture.BookRequestFixture;
import com.utm.stanislav.parkingapp.fixture.ParkingLotFixture;
import com.utm.stanislav.parkingapp.fixture.RPiBridgeFixture;
import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.model.enums.BookRequestStatus;
import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.SpaceFinderException;
import com.utm.stanislav.parkingapp.model.exceptions.StrategyResolverException;
import com.utm.stanislav.parkingapp.repository.BookRequestRepository;
import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import com.utm.stanislav.parkingapp.service.parking.ParkingService;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyHolder;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyResolver;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyType;
import com.utm.stanislav.parkingapp.service.spacefinder.strategy.SpaceFinderStrategy;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    
    @Mock
    private ParkingService parkingService;
    @Mock
    private SimpMessagingTemplate brokerMessagingTemplate;
    @Mock
    private FunctionMessageService functionMessageService;
    @Mock
    private SpaceFinderStrategyResolver spaceFinderStrategyResolver;
    @Mock
    private BookRequestRepository bookRequestRepository;
    @Mock
    SpaceFinderStrategy spaceFinderStrategy;
    
    @InjectMocks
    private BookRequestServiceImpl bookRequestService;
    
    private ParkingDto parkingDto;
    private User user;
    private SpaceFinderStrategyType spaceFinderStrategyType;
    
    @BeforeEach
    void setUp() {
        parkingDto = Mockito.mock(ParkingDto.class);
        user = new User();
        user.setUsername("mock");
        spaceFinderStrategy = Mockito.mock(SpaceFinderStrategy.class);
        spaceFinderStrategyType = SpaceFinderStrategyType.PLAIN;
    }
    
    @Test
    void createOne_whenParkingDoesNotExist_throwsBookingException() {
        when(parkingService.getParkingById(parkingDto.getId())).thenReturn(Optional.empty());
        
        assertThrows(BookingException.class, () -> bookRequestService.createOne(parkingDto, user));
        verify(parkingDto, atMost(2)).getName();
    }
    
    @Test
    void createOne_whenParkingDoesExistButNoFreeSpace_throwsBookingException()
            throws StrategyResolverException, SpaceFinderException {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        when(parkingService.getParkingById(parkingDto.getId())).thenReturn(Optional.of(parking));
        when(spaceFinderStrategyResolver.resolve(spaceFinderStrategyType)).thenReturn(spaceFinderStrategy);
        doThrow(SpaceFinderException.class).when(spaceFinderStrategy).findOne(parking);
        
        assertThrows(BookingException.class, () -> bookRequestService.createOne(parkingDto, user));
    }
    
    @Test
    void createOne_whenParkingDoesExistAndThereIsAFreeSpace_createsBookRequest()
            throws StrategyResolverException, SpaceFinderException {
        Parking parking = new Parking();
        parking.setId(UUID.randomUUID());
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        
        when(parkingService.getParkingById(parkingDto.getId())).thenReturn(Optional.of(parking));
        when(spaceFinderStrategyResolver.resolve(spaceFinderStrategyType)).thenReturn(spaceFinderStrategy);
        doReturn(parkingLot).when(spaceFinderStrategy).findOne(parking);
        
        when(parkingLot.getRPiBridge()).thenReturn(Mockito.mock(RPiBridge.class));
        lenient().doNothing().when(brokerMessagingTemplate).convertAndSendToUser(Mockito.anyString(), Mockito.anyString(), Mockito.any());
        
        assertDoesNotThrow(() -> bookRequestService.createOne(parkingDto, user));
        verify(bookRequestRepository).save(Mockito.any());
    }
    
    @Test
    void getFor_whenBookRequestDoesNotExist_returnsEmptyOptional() {
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        when(bookRequestRepository.findByParkingLot(parkingLot)).thenReturn(Optional.empty());
        
        assertThat(bookRequestService.getFor(parkingLot)).isEqualTo(Optional.empty());
        verify(bookRequestRepository).findByParkingLot(parkingLot);
    }
    
    @Test
    void getFor_whenBookRequestExists_returnsBookRequestOptional() {
        BookRequest bookRequest = Mockito.mock(BookRequest.class);
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        when(bookRequestRepository.findByParkingLot(parkingLot)).thenReturn(Optional.of(bookRequest));
        
        assertThat(bookRequestService.getFor(parkingLot)).isEqualTo(Optional.of(bookRequest));
        verify(bookRequestRepository).findByParkingLot(parkingLot);
    }
    
    @Test
    void confirmArrival_whenBookRequestDoesNotExists_throwsBookingException() {
        UUID randomUUID = UUID.randomUUID();
        when(bookRequestRepository.findById(randomUUID)).thenReturn(Optional.empty());
        
        assertThrows(BookingException.class, () -> bookRequestService.confirmArrival(randomUUID, user));
        verify(bookRequestRepository, never()).save(Mockito.any());
    }
    
    @Test
    void confirmArrival_whenBookRequestExistsButUserDoesNotMatch_throwsBookingException() {
        User user = UserFixture.usernameOnly("mockDifferent").get();
        BookRequest bookRequest = BookRequestFixture.withUser(user).get();
        User secondUser = UserFixture.usernameOnly("mock").get();
        UUID randomUUID = UUID.randomUUID();
        when(bookRequestRepository.findById(randomUUID)).thenReturn(Optional.of(bookRequest));
        
        assertThrows(BookingException.class, () -> bookRequestService.confirmArrival(randomUUID, secondUser));
        verify(bookRequestRepository, never()).save(Mockito.any());
    }
    
    @Test
    void confirmArrival_whenBookRequestExistsButInvalidStatus_throwsBookingException() {
        User user = UserFixture.usernameOnly("mockEqual").get();
        BookRequest bookRequest = BookRequestFixture.withUserAndStatus(user, BookRequestStatus.AWAITING_CONFIRMATION).get();
        UUID randomUUID = UUID.randomUUID();
        when(bookRequestRepository.findById(randomUUID)).thenReturn(Optional.of(bookRequest));
        
        assertThrows(BookingException.class, () -> bookRequestService.confirmArrival(randomUUID, user));
        verify(bookRequestRepository, never()).save(Mockito.any());
    }
    
    @Test
    void confirmArrival_whenBookRequestExistsAndIsValid_sendsUnblockMessageToPhysicalSpot() {
        User user = UserFixture.usernameOnly("mockEqual").get();
        RPiBridge rPiBridge = RPiBridgeFixture.withIsConnectedAndSessionId("random").get();
        ParkingLot parkingLot = ParkingLotFixture.withRPiBridge(rPiBridge).get();
        BookRequest bookRequest = BookRequestFixture
                                          .withUserAndStatusAndParkingLot(user, BookRequestStatus.CONFIRMED, parkingLot)
                                          .get();
        FunctionMessage functionMessage = Mockito.mock(FunctionMessage.class);
    
        UUID randomUUID = UUID.randomUUID();
        doReturn(Optional.of(bookRequest)).when(bookRequestRepository).findById(randomUUID);
        when(functionMessageService.generateFor(parkingLot, FunctionCode.UNBLOCK_LOT)).thenReturn(functionMessage);
        
        assertDoesNotThrow(() -> bookRequestService.confirmArrival(randomUUID, user));
        verify(brokerMessagingTemplate).convertAndSendToUser("random", "/messages/function",
                Collections.singletonList(functionMessage),
                createHeaders("random"));
    }
    
    private MessageHeaders createHeaders(String sessionId) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        stompHeaderAccessor.setSessionId(sessionId);
        stompHeaderAccessor.setLeaveMutable(Boolean.TRUE);
        return stompHeaderAccessor.getMessageHeaders();
    }
}
