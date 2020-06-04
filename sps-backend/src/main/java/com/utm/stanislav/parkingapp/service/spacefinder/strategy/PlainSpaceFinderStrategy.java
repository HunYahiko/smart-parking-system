package com.utm.stanislav.parkingapp.service.spacefinder.strategy;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.exceptions.SpaceFinderException;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyType;
import com.utm.stanislav.parkingapp.service.spacefinder.strategy.SpaceFinderStrategy;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class PlainSpaceFinderStrategy implements SpaceFinderStrategy {
    
    private final ParkingLotService parkingLotService;
    
    @Override
    public ParkingLot findOne(Parking parking) throws SpaceFinderException {
        return parkingLotService.getOneFreeRandomFrom(parking)
                .orElseThrow(() -> new SpaceFinderException("Could not find a free space in " + parking.getLogicalId() + " parking."));
    }
    
    @Override
    public SpaceFinderStrategyType getType() {
        return SpaceFinderStrategyType.PLAIN;
    }
}
