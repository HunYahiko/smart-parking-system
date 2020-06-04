package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.web.dto.RoleDto;

import javax.inject.Named;

@Named
public class RoleToDtoMapper implements Mapper<Role, RoleDto> {
    
    @Override
    public RoleDto map(Role entity) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(entity.getName());
        return roleDto;
    }
}
