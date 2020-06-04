package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.web.dto.LevelNameListingDto;

import java.util.List;
import java.util.Optional;

public interface LevelService {
    
    List<Level> getAllFrom(Parking parking);
    Optional<Level> getRandomFrom(Parking parking);
    List<LevelNameListingDto> getNameListingFrom(Parking parking);
}
