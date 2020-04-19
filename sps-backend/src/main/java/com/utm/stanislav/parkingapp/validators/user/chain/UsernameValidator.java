package com.utm.stanislav.parkingapp.validators.user.chain;

import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import com.utm.stanislav.parkingapp.validators.Validator;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@AllArgsConstructor
public class UsernameValidator implements ValidationChain<User> {
    
    private UserRepository userRepository;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void validate(User user) throws UserValidationException {
        Boolean userExists = userRepository.existsByUsername(user.getUsername());
        if (userExists) throw new UserValidationException("Username is taken");
    }
    
    @Override
    public Integer getOrder() {
        return 0;
    }
}
