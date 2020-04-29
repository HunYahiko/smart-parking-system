package com.utm.stanislav.parkingapp.service.booking.strategy;

import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.service.level.LevelService;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class PlainBookingStrategy implements BookingStrategy {
    
    private final LevelService levelService;
    private final ParkingLotService parkingLotService;
    
    @Override
    @Transactional
    public ParkingLot book(Parking parking) throws BookingException {
        Level randomLevel = this.levelService.getRandomLevelFrom(parking);
        return parkingLotService.getFreeRandomParkingLotFrom(randomLevel)
                .orElseThrow(() -> new BookingException("Failed to find a free parking spot"));
    }
}
