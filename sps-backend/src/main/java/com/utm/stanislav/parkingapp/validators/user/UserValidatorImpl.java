package com.utm.stanislav.parkingapp.validators.user;

import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {
    
    private final List<ValidationChain<User>> userValidators;
    
    @Override
    @Transactional
    public void validateUser(User user) throws UserValidationException {
        for (ValidationChain<User> validationChain : userValidators) {
            try {
                validationChain.validate(user);
            } catch (ValidationException ex) {
                throw (UserValidationException) ex;
            }
        }
    }
}
