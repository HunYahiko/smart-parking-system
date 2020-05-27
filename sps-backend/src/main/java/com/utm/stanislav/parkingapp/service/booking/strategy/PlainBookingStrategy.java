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
import java.util.List;
import java.util.Optional;

@Named
@RequiredArgsConstructor
public class PlainBookingStrategy implements BookingStrategy {
    
    private final LevelService levelService;
    private final ParkingLotService parkingLotService;
    
    @Override
    @Transactional
    public ParkingLot book(Parking parking) throws BookingException {
        List<Level> levels = levelService.getLevelsFromParking(parking);
        for (Level level: levels) {
            Optional<ParkingLot> parkingLotOptional = parkingLotService.getFreeRandomParkingLotFrom(level);
            if (parkingLotOptional.isPresent()) {
                return parkingLotOptional.get();
            }
        }
        throw new BookingException("Failed to find a free parking spot in parking[" + parking.getLogicalId() + "]");
    }
}
