package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.web.dto.RPiBridgeDto;

import javax.inject.Named;

@Named
public class RPiBridgeToDtoMapper implements Mapper<RPiBridge, RPiBridgeDto> {
    
    @Override
    public RPiBridgeDto map(RPiBridge entity) {
        RPiBridgeDto rPiBridgeDto = new RPiBridgeDto();
        rPiBridgeDto.setId(entity.getId());
        rPiBridgeDto.setLogicalId(entity.getLogicalId());
        rPiBridgeDto.setIsConnected(entity.getIsConnected());
        return rPiBridgeDto;
    }
}
