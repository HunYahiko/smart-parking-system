package com.utm.stanislav.parkingapp.fixture;

import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RoleFixture {
    private String name;
    private List<User> users;
    
    public static RoleFixture withName(String name) {
        return RoleFixture.builder().name(name).users(new ArrayList<>()).build();
    }
    
    public Role get() {
        return new Role(name, users);
    }
}
