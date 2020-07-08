package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuickParkingInfoDto {
    private final ParkingDto parking;
    private final AddressDto address;
    private final Long totalParkingLots;
    private final Long freeParkingLots;
    private final ChargingType chargingType;
}
