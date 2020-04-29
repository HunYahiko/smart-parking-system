package com.utm.stanislav.parkingapp.model.exceptions;

public class UserNotFoundException extends Exception {
    
    public UserNotFoundException() {
    }
    
    public UserNotFoundException(String message) {
        super(message);
    }
}
