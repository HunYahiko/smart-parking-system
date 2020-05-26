package com.utm.stanislav.parkingapp.model;

import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class LayoutUpdateMessage {
    
    private final String parkingLotName;
    private final UUID layoutObjectId;
    private final ParkingStatus parkingStatus;
}
