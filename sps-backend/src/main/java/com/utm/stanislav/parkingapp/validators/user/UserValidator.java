package com.utm.stanislav.parkingapp.validators.user;

import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;

public interface UserValidator {
    
    void validateUser(User user) throws UserValidationException;
}
