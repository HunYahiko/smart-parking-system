package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ParkingLocationDto {
    private final ParkingDto parking;
    private final AddressDto address;
    private final ChargingType chargingType;
}
