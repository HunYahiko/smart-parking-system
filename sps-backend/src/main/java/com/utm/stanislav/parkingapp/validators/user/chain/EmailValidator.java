package com.utm.stanislav.parkingapp.validators.user.chain;

import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
@Order(2)
public class EmailValidator implements ValidationChain<User> {
    
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public void validate(User user) throws UserValidationException {
        Boolean userExists = userRepository.existsByEmail(user.getEmail());
        if (userExists) throw new UserValidationException("This email is used!", ValidationInputField.EMAIL);
    }
}
