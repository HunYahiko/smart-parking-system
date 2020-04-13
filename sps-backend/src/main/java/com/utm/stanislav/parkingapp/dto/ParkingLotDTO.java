package com.utm.stanislav.parkingapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingLotDTO {
    private String name;
    @JsonProperty("level")
    private LevelDTO levelDTO;
}
