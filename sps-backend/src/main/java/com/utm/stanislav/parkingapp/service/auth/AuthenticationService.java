package com.utm.stanislav.parkingapp.service.auth;

import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDTO;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;

public interface AuthenticationService {
    
    AuthenticationResponse authenticate(LoginDTO loginDTO) throws InvalidCredentialsException;
}
