package com.utm.stanislav.parkingapp.web.controller.user;

import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.web.dto.SignUpExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.utm.stanislav.parkingapp.web.controller.user")
public class UserControllerAdvice {
    
    @ExceptionHandler(value = UserValidationException.class)
    public ResponseEntity<SignUpExceptionDTO> handleUserValidationException(UserValidationException ex) {
        SignUpExceptionDTO signUpExceptionDTO = new SignUpExceptionDTO(ex.getMessage(), ex.getInputField().getName());
        return ResponseEntity.badRequest().body(signUpExceptionDTO);
    }
}
