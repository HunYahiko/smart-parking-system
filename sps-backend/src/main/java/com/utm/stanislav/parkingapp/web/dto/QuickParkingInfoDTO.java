package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuickParkingInfoDTO {
    private final ParkingDTO parking;
    private final AddressDTO address;
    private final Long totalParkingLots;
    private final Long freeParkingLots;
    private final ChargingType chargingType;
}
