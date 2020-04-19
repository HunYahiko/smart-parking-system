package com.utm.stanislav.parkingapp.validators.user;

import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import com.utm.stanislav.parkingapp.validators.user.chain.PhoneNumberValidator;
import com.utm.stanislav.parkingapp.validators.user.chain.UsernameValidator;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Named
@AllArgsConstructor
public class UserValidatorImpl implements UserValidator {
    
    private List<ValidationChain<User>> userValidators;
    
    @PostConstruct
    public void sortValidators() {
        userValidators.sort(Comparator.comparing(ValidationChain::getOrder));
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void validateUser(User user) throws UserValidationException {
        for (ValidationChain<User> validationChain : userValidators) {
            try {
                validationChain.validate(user);
            } catch (ValidationException ex) {
                throw new UserValidationException(ex.getMessage());
            }
        }
    }
}
