package com.utm.stanislav.parkingapp.controller.auth;

import com.utm.stanislav.parkingapp.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.dto.LoginDTO;
import com.utm.stanislav.parkingapp.exceptions.InvalidCredentialsException;
import com.utm.stanislav.parkingapp.service.auth.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/auth")
public class AuthController {
    
    private AuthenticationService authenticationService;
    
    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @PostMapping("/sing-in")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginDTO loginDTO)
            throws InvalidCredentialsException {
        AuthenticationResponse response = this.authenticationService.authenticate(loginDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
