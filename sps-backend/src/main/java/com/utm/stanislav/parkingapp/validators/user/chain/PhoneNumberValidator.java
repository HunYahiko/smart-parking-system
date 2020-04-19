package com.utm.stanislav.parkingapp.validators.user.chain;

import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.exceptions.ValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.validators.ValidationChain;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;

@Named
@AllArgsConstructor
public class PhoneNumberValidator implements ValidationChain<User> {
    
    private UserRepository userRepository;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void validate(User user) throws UserValidationException {
        Boolean userExists = userRepository.existsByPhoneNumber(user.getPhoneNumber());
        if (userExists) throw new UserValidationException("This phone number is taken!");
    }
    
    @Override
    public Integer getOrder() {
        return 0;
    }
}
