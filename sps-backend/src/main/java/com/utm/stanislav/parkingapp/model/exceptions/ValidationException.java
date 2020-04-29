package com.utm.stanislav.parkingapp.model.exceptions;

public class ValidationException extends Exception {
    
    public ValidationException() {
    }
    
    public ValidationException(String message) {
        super(message);
    }
}
