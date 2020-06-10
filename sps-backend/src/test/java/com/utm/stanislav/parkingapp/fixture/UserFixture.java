package com.utm.stanislav.parkingapp.fixture;

import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserFixture {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private ParkingLot parkingLot;
    private List<Role> roles;
    
    public static UserFixture usernameOnly(String username) {
        return UserFixture.builder().username(username).build();
    }
    
    public static UserFixture withUsernameAndPassword(String username, String password) {
        return UserFixture.builder().username(username).password(password).build();
    }
    
    public User get() {
        return new User(firstName, lastName, username, email, password, phoneNumber, parkingLot, roles);
    }
}
