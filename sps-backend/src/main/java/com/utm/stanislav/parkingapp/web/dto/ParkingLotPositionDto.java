package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParkingLotPositionDto {
    
    private final String name;
    
    @JsonProperty("xPosition")
    private final Integer xPosition;
    
    @JsonProperty("yPosition")
    private final Integer yPosition;
}
