package com.utm.stanislav.parkingapp.web.controller.user;

import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.web.dto.SignUpExceptionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.utm.stanislav.parkingapp.web.controller.user")
public class UserControllerAdvice {
    
    @ExceptionHandler(value = UserValidationException.class)
    public ResponseEntity<SignUpExceptionDto> handleUserValidationException(UserValidationException ex) {
        SignUpExceptionDto signUpExceptionDTO = new SignUpExceptionDto(ex.getMessage(), ex.getInputField().getName());
        return ResponseEntity.badRequest().body(signUpExceptionDTO);
    }
}
