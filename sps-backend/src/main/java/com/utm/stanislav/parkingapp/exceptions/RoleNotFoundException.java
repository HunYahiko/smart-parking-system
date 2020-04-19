package com.utm.stanislav.parkingapp.exceptions;

import lombok.AllArgsConstructor;

public class RoleNotFoundException extends Exception {
    
    public RoleNotFoundException() {
    }
    
    public RoleNotFoundException(String message) {
        super(message);
    }
}
