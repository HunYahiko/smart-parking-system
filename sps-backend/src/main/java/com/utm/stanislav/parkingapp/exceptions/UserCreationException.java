package com.utm.stanislav.parkingapp.exceptions;

import lombok.AllArgsConstructor;

public class UserCreationException extends Exception {
    
    public UserCreationException() {
    }
    
    public UserCreationException(String message) {
        super(message);
    }
}
