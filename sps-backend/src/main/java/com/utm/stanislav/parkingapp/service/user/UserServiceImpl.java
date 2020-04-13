package com.utm.stanislav.parkingapp.service.user;

import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    
    private UserRepository userRepository;
    
    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
