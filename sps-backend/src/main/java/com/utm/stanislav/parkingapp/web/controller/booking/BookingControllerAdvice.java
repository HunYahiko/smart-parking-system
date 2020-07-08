package com.utm.stanislav.parkingapp.web.controller.booking;

import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice("com.utm.stanislav.parkingapp.web.controller.booking")
public class BookingControllerAdvice {
    
    @ExceptionHandler(value = BookingException.class)
    public ResponseEntity<?> handleBookingException(BookingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
}
