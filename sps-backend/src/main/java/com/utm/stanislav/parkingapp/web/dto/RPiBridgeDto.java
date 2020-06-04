package com.utm.stanislav.parkingapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RPiBridgeDto {
    private UUID id;
    private String logicalId;
    private Boolean isConnected;
}
