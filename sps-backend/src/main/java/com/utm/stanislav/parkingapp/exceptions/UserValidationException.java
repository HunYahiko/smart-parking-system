package com.utm.stanislav.parkingapp.exceptions;

public class UserValidationException extends ValidationException {
    
    public UserValidationException() {
    }
    
    public UserValidationException(String message) {
        super(message);
    }
}
