package com.utm.stanislav.parkingapp.exceptions;

public class InvalidCredentialsException extends Exception {
    
    public InvalidCredentialsException() {
    }
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
