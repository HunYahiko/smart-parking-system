package com.utm.stanislav.parkingapp.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ParkingLayoutDto {
    private final List<ParkingLotPositionDto> parkingLotsPositions;
    private final Integer rows;
    private final Integer columns;
}
