package com.utm.stanislav.parkingapp.booking;

import com.utm.stanislav.parkingapp.exceptions.BookingException;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.service.level.LevelService;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PlainBookingStrategy implements BookingStrategy {
    
    private LevelService levelService;
    private ParkingLotService parkingLotService;
    
    @Inject
    public void setLevelService(LevelService levelService) {
        this.levelService = levelService;
    }
    
    @Inject
    public void setParkingLotService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }
    
    @Override
    @Transactional
    public ParkingLot book(Parking parking) throws BookingException {
        Level randomLevel = this.levelService.getRandomLevelFrom(parking);
        return parkingLotService.getFreeRandomParkingLotFrom(randomLevel)
                .orElseThrow(() -> new BookingException("Failed to find a free parking spot"));
    }
}
