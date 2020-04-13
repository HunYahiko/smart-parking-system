package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Parking;

import java.util.Optional;

public interface ParkingService {
    
    Optional<Parking> getParkingByName(String name);
}
