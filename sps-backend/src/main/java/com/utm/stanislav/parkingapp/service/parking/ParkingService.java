package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLocationDTO;
import com.utm.stanislav.parkingapp.web.dto.QuickParkingInfoDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingService {
    
    Optional<Parking> getParkingByName(String name);
    Optional<Parking> getParkingById(UUID uuid);
    List<ParkingLocationDTO> fetchParkingsLocations();
    QuickParkingInfoDTO getQuickParkingInfo(UUID parkingId) throws ParkingNotFoundException;
}
