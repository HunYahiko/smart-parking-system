package com.utm.stanislav.parkingapp.web.dto;

import com.utm.stanislav.parkingapp.model.enums.BookRequestStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BookRequestDto {
    private final UUID id;
    private final String parking;
    private final String parkingLot;
    private final BookRequestStatus bookRequestStatus;
}
