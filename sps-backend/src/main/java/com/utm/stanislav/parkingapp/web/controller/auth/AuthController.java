package com.utm.stanislav.parkingapp.web.controller.auth;

import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDto;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;
import com.utm.stanislav.parkingapp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("/sing-in")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginDto loginDTO)
            throws InvalidCredentialsException {
        AuthenticationResponse response = this.authenticationService.authenticate(loginDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
