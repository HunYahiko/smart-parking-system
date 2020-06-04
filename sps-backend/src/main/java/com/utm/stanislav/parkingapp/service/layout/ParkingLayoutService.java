package com.utm.stanislav.parkingapp.service.layout;

import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDto;

import java.util.UUID;

public interface ParkingLayoutService {

    LevelLayoutDto getLevelLayout(UUID levelId) throws Exception;
}
