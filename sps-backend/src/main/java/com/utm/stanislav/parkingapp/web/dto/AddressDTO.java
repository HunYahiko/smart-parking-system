package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.Coordinates;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressDTO {
    private final String streetName;
    private final String streetNumber;
    private final Coordinates coordinates;
}
