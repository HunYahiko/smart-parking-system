package com.utm.stanislav.parkingapp.web.controller.booking;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.service.user.UserService;
import com.utm.stanislav.parkingapp.web.dto.BookRequestDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotDto;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.booking.BookRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    
    private final BookRequestService bookRequestService;
    private final UserService userService;
    
    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> bookSpotIn(@RequestBody ParkingDto parkingDTO)
            throws BookingException {
        String username = fetchUsername();
        User user = userService.getByUsername(username)
                .orElseThrow(() -> new BookingException("Could not resolve user that requested!"));
        log.info("User [{}] requested a parking lot in [{}] parking.", username, parkingDTO.getName());
        bookRequestService.createOne(parkingDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PostMapping("/confirm/{id}")
    public ResponseEntity<?> confirmArrival(@PathVariable("id") UUID bookRequestId) throws BookingException {
        String username = fetchUsername();
        User user = userService.getByUsername(username)
                .orElseThrow(() -> new BookingException("Could not resolve user that tries to confirm!"));
        bookRequestService.confirmArrival(bookRequestId, user);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookRequestDto>> getAllForUser() throws UserNotFoundException {
        String username = fetchUsername();
        User user = userService.getByUsername(username)
                            .orElseThrow(UserNotFoundException::new);
        List<BookRequestDto> bookRequestDtoList = bookRequestService.getByUser(user);
        return ResponseEntity.ok(bookRequestDtoList);
    }
    
    private String fetchUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUsername();
    }
}
