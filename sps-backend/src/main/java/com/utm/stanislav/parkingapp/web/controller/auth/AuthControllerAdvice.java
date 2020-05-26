package com.utm.stanislav.parkingapp.web.controller.auth;

import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.utm.stanislav.parkingapp.web.controller.auth")
public class AuthControllerAdvice {
    
    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.notFound().build();
    }
}
