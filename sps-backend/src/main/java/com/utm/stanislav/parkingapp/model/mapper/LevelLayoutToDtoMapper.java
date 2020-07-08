package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.LayoutObject;
import com.utm.stanislav.parkingapp.model.LevelLayout;
import com.utm.stanislav.parkingapp.web.dto.LayoutObjectDto;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class LevelLayoutToDtoMapper implements Mapper<LevelLayout, LevelLayoutDto> {
    
    private final Mapper<LayoutObject, LayoutObjectDto> layoutObjectDtoMapper;
    
    @Override
    public LevelLayoutDto map(LevelLayout entity) {
        return null;
    }
}
