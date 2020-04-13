package com.utm.stanislav.parkingapp.service.userdetails;

import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Service
@Named(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private UserService userService;
    
    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = this.userService.getUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " +  username + " was not found!"));
        
        return UserPrincipal.create(user);
    }
}
