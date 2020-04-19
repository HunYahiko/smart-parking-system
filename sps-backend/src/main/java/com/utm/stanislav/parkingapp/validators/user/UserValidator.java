package com.utm.stanislav.parkingapp.validators.user;

import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.validators.Validator;

public interface UserValidator {
    
    void validateUser(User user) throws UserValidationException;
}
