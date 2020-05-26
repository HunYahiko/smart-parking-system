package com.utm.stanislav.parkingapp.model.exceptions;

public class LevelNotFoundException extends Exception {
    
    public LevelNotFoundException() {
    }
    
    public LevelNotFoundException(String message) {
        super(message);
    }
}
