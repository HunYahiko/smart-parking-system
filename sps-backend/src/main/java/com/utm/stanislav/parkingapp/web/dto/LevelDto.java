package com.utm.stanislav.parkingapp.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class LevelDto {
    private UUID id;
    private String logicalId;
    private ParkingDto parkingDto;
    private List<RPiBridgeDto> rPiBridgeDtos;
    private LevelLayoutDto levelLayoutDto;
}
