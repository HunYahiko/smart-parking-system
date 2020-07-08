package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.model.LayoutObject;
import lombok.*;

import javax.inject.Named;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelLayoutDto {
    
    @JsonProperty("layoutObjects")
    private List<LayoutObject> layoutObjects;
    
    @JsonProperty("width")
    private Integer width;
    
    @JsonProperty("length")
    private Integer length;
}
