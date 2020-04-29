package com.utm.stanislav.parkingapp.validators.user.chain;

import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
@Order(1)
public class UsernameValidator implements ValidationChain<User> {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void validate(User user) throws UserValidationException {
        Boolean userExists = userRepository.existsByUsername(user.getUsername());
        if (userExists) throw new UserValidationException("Username is taken");
    }
}
