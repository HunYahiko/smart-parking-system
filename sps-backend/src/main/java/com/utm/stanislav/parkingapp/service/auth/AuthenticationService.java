package com.utm.stanislav.parkingapp.service.auth;

import com.utm.stanislav.parkingapp.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.dto.LoginDTO;
import com.utm.stanislav.parkingapp.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    
    AuthenticationResponse authenticate(LoginDTO loginDTO) throws InvalidCredentialsException;
}
