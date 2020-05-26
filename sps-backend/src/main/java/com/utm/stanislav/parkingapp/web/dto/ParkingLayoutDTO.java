package com.utm.stanislav.parkingapp.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ParkingLayoutDTO {
    private final List<ParkingLotPositionDTO> parkingLotsPositions;
    private final Integer rows;
    private final Integer columns;
}
