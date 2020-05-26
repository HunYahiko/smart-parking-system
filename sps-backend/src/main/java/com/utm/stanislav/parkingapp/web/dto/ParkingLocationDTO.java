package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.Coordinates;
import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParkingLocationDTO {
    private final ParkingDTO parking;
    private final AddressDTO address;
    private final ChargingType chargingType;
}
