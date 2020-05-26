package com.utm.stanislav.parkingapp.web.dto;

import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ParkingDTO {
    private final UUID id;
    private final String name;
}
