package com.utm.stanislav.parkingapp.model.exceptions;

public class UserCreationException extends Exception {
    
    public UserCreationException() {
    }
    
    public UserCreationException(String message) {
        super(message);
    }
}
