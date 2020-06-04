package com.utm.stanislav.parkingapp.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
public class LevelNameListingDto {
    private UUID levelId;
    private String levelName;
}
