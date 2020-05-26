package com.utm.stanislav.parkingapp.model.exceptions;

public class ParkingNotFoundException extends Exception {
    
    public ParkingNotFoundException() {
    }
    
    public ParkingNotFoundException(String message) {
        super(message);
    }
}
