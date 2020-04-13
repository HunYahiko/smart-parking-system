package com.utm.stanislav.parkingapp.service.level;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;

import java.util.List;

public interface LevelService {
    
    List<Level> getLevelsFromParking(Parking parking);
    Level getRandomLevelFrom(Parking parking);
}
