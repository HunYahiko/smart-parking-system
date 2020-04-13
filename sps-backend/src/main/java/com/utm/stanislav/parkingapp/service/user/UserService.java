package com.utm.stanislav.parkingapp.service.user;

import com.utm.stanislav.parkingapp.model.User;

import java.util.Optional;

public interface UserService {
    
    Optional<User> getUserByUsername(String username);
}
