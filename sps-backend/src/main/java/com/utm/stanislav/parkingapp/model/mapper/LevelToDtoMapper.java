package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.LevelLayout;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.web.dto.LevelDto;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDto;
import com.utm.stanislav.parkingapp.web.dto.RPiBridgeDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class LevelToDtoMapper implements Mapper<Level, LevelDto> {
    
    private final Mapper<RPiBridge, RPiBridgeDto> rPiBridgeDtoMapper;
    private final Mapper<LevelLayout, LevelLayoutDto> levelLayoutDtoMapper;
    
    @Override
    public LevelDto map(Level entity) {
        LevelDto levelDto = new LevelDto();
        levelDto.setId(entity.getId());
        levelDto.setLogicalId(entity.getLogicalId());
        levelDto.setRPiBridgeDtos(rPiBridgeDtoMapper.mapList(entity.getRPiBridge()));
        levelDto.setLevelLayoutDto(levelLayoutDtoMapper.map(entity.getLevelLayout()));
        return levelDto;
    }
}
