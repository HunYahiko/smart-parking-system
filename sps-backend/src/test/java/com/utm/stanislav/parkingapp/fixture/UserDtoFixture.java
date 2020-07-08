package com.utm.stanislav.parkingapp.fixture;

import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.web.dto.RoleDto;
import com.utm.stanislav.parkingapp.web.dto.UserDto;
import lombok.Builder;
import lombok.Data;

import javax.inject.Named;
import java.util.List;

@Data
@Builder
public class UserDtoFixture {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private List<RoleDto> roles;
    
    public static UserDtoFixture usernameOnly(String username) {
        return UserDtoFixture.builder().username(username).build();
    }
    
    public UserDto get() {
        return new UserDto(firstName, lastName, username, email, password, phoneNumber, roles);
    }
    
    
}
