package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.service.booking.strategy.BookingStrategy;
import com.utm.stanislav.parkingapp.web.dto.LevelDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotDTO;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.service.parking.ParkingService;
import com.utm.stanislav.parkingapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    
    private final ApplicationContext context;
    private final ParkingService parkingService;
    private final UserService userService;
    private final BookingStrategySelectorMBean bookingStrategySelector;
    
    @Override
    @Transactional
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 2000)
    )
    public ParkingLotDTO book(ParkingDTO parkingDTO, String username) throws BookingException {
        log.info("Booking a parking lot for user [{}]", username.toUpperCase());
        Parking parking = this.parkingService.getParkingByName(parkingDTO.getName())
                .orElseThrow(() -> new BookingException(
                        "Parking with name " + parkingDTO.getName() + " could not be found!"));
        
        BookingStrategy bookingStrategy = fetchStrategyOrReturnDefault();
        ParkingLot bookedParkingLot = bookingStrategy.book(parking);
        log.info("Found suitable parking lot [{}] at level [{}]",
                bookedParkingLot.getLogicalId(), bookedParkingLot.getLevel().getLogicalId());
        
        User user = this.userService.getUserByUsername(username)
                .orElseThrow(() -> new BookingException(
                        "User requesting booking was not found!"));
        
        bookedParkingLot.setParkingStatus(ParkingStatus.BOOKED);
        bookedParkingLot.setBookedBy(user);
        user.setParkingLot(bookedParkingLot);
        log.info("Successfully finished booking a place for user [{}]", user.getUsername());
    
        LevelDTO levelDTO = new LevelDTO(bookedParkingLot.getLevel().getLogicalId());
        return new ParkingLotDTO(bookedParkingLot.getLogicalId() ,levelDTO);
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
    
}
