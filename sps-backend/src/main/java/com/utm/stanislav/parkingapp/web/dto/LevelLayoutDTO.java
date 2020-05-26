package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.model.LayoutObject;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class LevelLayoutDTO {
    
    @JsonProperty("layoutObjects")
    private final List<LayoutObject> layoutObjects;
    
    @JsonProperty("width")
    private final Integer width;
    
    @JsonProperty("length")
    private final Integer length;
}
