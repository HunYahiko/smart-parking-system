package com.utm.stanislav.parkingapp.service.spacefinder.strategy;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.exceptions.SpaceFinderException;
import com.utm.stanislav.parkingapp.service.spacefinder.helper.SpaceFinderStrategyType;

public interface SpaceFinderStrategy {
    
    ParkingLot findOne(Parking parking) throws SpaceFinderException;
    
    SpaceFinderStrategyType getType();
}
