package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.LayoutObject;
import com.utm.stanislav.parkingapp.web.dto.LayoutObjectDto;

import javax.inject.Named;

@Named
public class LayoutObjectToDtoMapper implements Mapper<LayoutObject, LayoutObjectDto> {
    
    @Override
    public LayoutObjectDto map(LayoutObject entity) {
        LayoutObjectDto layoutObjectDto = new LayoutObjectDto();
        layoutObjectDto.setId(entity.getId());
        layoutObjectDto.setType(entity.getType());
        layoutObjectDto.setOrientation(entity.getOrientation());
        layoutObjectDto.setXPosition(entity.getXPosition());
        layoutObjectDto.setYPosition(entity.getYPosition());
        return layoutObjectDto;
    }
}
