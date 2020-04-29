package com.utm.stanislav.parkingapp.service.auth;

import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDTO;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.security.jwt.JwtProvider;
import com.utm.stanislav.parkingapp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    
    @Override
    @Transactional
    public AuthenticationResponse authenticate(LoginDTO loginDTO) throws InvalidCredentialsException {
        String loginUsername = loginDTO.getUsername();
        User user = this.userService.getUserByUsername(loginUsername)
                .orElseThrow(InvalidCredentialsException::new);
        String rawPassword = loginDTO.getPassword();
        String encodedPassword = user.getPassword();
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUsername, rawPassword));
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String jwtToken = jwtProvider.generateToken(authentication);
            return AuthenticationResponse.from(jwtToken);
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
