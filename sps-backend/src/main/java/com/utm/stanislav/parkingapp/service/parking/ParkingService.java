package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.web.dto.ParkingLocationDto;
import com.utm.stanislav.parkingapp.web.dto.QuickParkingInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingService {
    
    Optional<Parking> getParkingByName(String name);
    Optional<Parking> getParkingById(UUID uuid);
    List<ParkingLocationDto> fetchParkingsLocations();
    QuickParkingInfoDto getQuickParkingInfo(UUID parkingId) throws ParkingNotFoundException;
}
