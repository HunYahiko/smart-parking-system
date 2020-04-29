package com.utm.stanislav.parkingapp.service.user;

import com.utm.stanislav.parkingapp.model.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.service.role.RoleService;
import com.utm.stanislav.parkingapp.validators.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserValidator userValidator;
    
    @Override
    @Transactional
    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
    
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    
    @Override
    @Transactional
    public void createUser(User user) throws UserValidationException {
        userValidator.validateUser(user);
        user.setId(UUID.randomUUID());
        setBaseRole(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void updateUsername(String newUsername, String username)
            throws UserValidationException, UserNotFoundException {
        boolean usernameExists = userRepository.existsByUsername(newUsername);
        if (usernameExists) throw new UserValidationException("New username is taken!");
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        user.setUsername(newUsername);
    }
    
    @Override
    @Transactional
    public void deleteUser(String username) {
        this.userRepository.deleteByUsername(username);
    }
    
    private void setBaseRole(User user) {
        Role baseRole = this.roleService.getBaseRole();
        List<Role> roles = new ArrayList<>();
        roles.add(baseRole);
        user.setRoles(roles);
    }
}
