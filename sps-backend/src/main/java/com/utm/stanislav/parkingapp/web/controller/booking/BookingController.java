package com.utm.stanislav.parkingapp.web.controller.booking;

import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotDTO;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    
    private final BookingService bookingService;
    
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ParkingLotDTO> bookSpotIn(@RequestBody ParkingDTO parkingDTO)
            throws BookingException {
        String username = fetchUsername();
        log.info("User [{}] requested a parking lot in [{}] parking.", username, parkingDTO.getName());
        ParkingLotDTO bookedParkingLot = this.bookingService.book(parkingDTO, username);
        return ResponseEntity.ok(bookedParkingLot);
    }
    
    private String fetchUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUsername();
    }
}
