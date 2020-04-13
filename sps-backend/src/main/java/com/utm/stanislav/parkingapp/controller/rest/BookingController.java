package com.utm.stanislav.parkingapp.controller.rest;

import com.utm.stanislav.parkingapp.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.dto.ParkingLotDTO;
import com.utm.stanislav.parkingapp.exceptions.BookingException;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.booking.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/booking")
@AllArgsConstructor
public class BookingController {
    
    private BookingService bookingService;
    
    @PostMapping("/")
    public ResponseEntity<ParkingLotDTO> bookSpotIn(@RequestBody ParkingDTO parkingDTO)
            throws BookingException {
        String username = fetchUsername();
        ParkingLotDTO bookedParkingLot = this.bookingService.book(parkingDTO, username);
        return ResponseEntity.ok(bookedParkingLot);
    }
    
    private String fetchUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUsername();
    }
}
