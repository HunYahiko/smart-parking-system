package com.utm.stanislav.parkingapp.model.exceptions;

public class RoleNotFoundException extends Exception {
    
    public RoleNotFoundException() {
    }
    
    public RoleNotFoundException(String message) {
        super(message);
    }
}
