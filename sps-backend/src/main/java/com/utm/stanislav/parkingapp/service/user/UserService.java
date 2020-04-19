package com.utm.stanislav.parkingapp.service.user;

import com.utm.stanislav.parkingapp.dto.UserDTO;
import com.utm.stanislav.parkingapp.exceptions.UserCreationException;
import com.utm.stanislav.parkingapp.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    void createUser(User user) throws UserValidationException;
    void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException;
    void deleteUser(String username);
}
