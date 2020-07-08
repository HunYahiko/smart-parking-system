package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.web.dto.UserDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class UserToDtoMapper implements Mapper<User, UserDto> {
    
    private final RoleToDtoMapper roleToDtoMapper;
    
    @Override
    public UserDto map(User entity) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setPhoneNumber(entity.getPhoneNumber());
        userDto.setRoles(roleToDtoMapper.mapList(entity.getRoles()));
        return userDto;
    }
}
