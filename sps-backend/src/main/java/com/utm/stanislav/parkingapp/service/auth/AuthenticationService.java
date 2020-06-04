package com.utm.stanislav.parkingapp.service.auth;

import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDto;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    
    AuthenticationResponse authenticate(LoginDto loginDTO) throws InvalidCredentialsException;
}
