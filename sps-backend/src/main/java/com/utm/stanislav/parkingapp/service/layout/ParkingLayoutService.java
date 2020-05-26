package com.utm.stanislav.parkingapp.service.layout;

import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLayoutDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotPositionDTO;

import java.util.List;
import java.util.UUID;

public interface ParkingLayoutService {

    LevelLayoutDTO getLevelLayout(UUID levelId) throws Exception;
}
