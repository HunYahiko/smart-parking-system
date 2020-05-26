package com.utm.stanislav.parkingapp.web.controller.parking;

import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.service.parking.ParkingService;
import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLocationDTO;
import com.utm.stanislav.parkingapp.web.dto.QuickParkingInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/parkings")
@Slf4j
@RequiredArgsConstructor
public class ParkingController {
    
    private final ParkingService parkingService;
    
    @GetMapping("/locations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ParkingLocationDTO>> getParkingsLocations() {
        List<ParkingLocationDTO> parkingLocations = parkingService.fetchParkingsLocations();
        return ResponseEntity.ok(parkingLocations);
    }
    
    @GetMapping("{id}/info/quick")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuickParkingInfoDTO> getQuickInfo(@PathVariable("id") UUID parkingId)
            throws ParkingNotFoundException {
        QuickParkingInfoDTO quickParkingInfo = parkingService.getQuickParkingInfo(parkingId);
        return ResponseEntity.ok(quickParkingInfo);
    }
}
