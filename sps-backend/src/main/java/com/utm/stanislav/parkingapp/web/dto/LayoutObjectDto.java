package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectOrientation;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LayoutObjectDto {
    private UUID id;
    private LayoutObjectType type;
    private LayoutObjectOrientation orientation;
    
    @JsonProperty("x")
    private Integer xPosition;
    
    @JsonProperty("y")
    private Integer yPosition;
}
