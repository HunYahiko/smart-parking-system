package com.utm.stanislav.parkingapp.web.controller.parking;

import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.utm.stanislav.parkingapp.web.controller.parking")
public class ParkingControllerAdvice {
    
    @ExceptionHandler(value = ParkingNotFoundException.class)
    public ResponseEntity<?> handleParkingNotFoundException(ParkingNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
