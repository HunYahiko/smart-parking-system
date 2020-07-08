package com.utm.stanislav.parkingapp.model.mapper;

import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.web.dto.UserDto;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;

@Named
public class UserDtoToEntityMapper implements Mapper<UserDto, User> {
    
    @Override
    public User map(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}
