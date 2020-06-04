package com.utm.stanislav.parkingapp.web.controller.layout;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.service.layout.ParkingLayoutService;
import com.utm.stanislav.parkingapp.service.level.LevelService;
import com.utm.stanislav.parkingapp.service.parking.ParkingService;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDto;
import com.utm.stanislav.parkingapp.web.dto.LevelNameListingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/layouts")
@RequiredArgsConstructor
public class ParkingLayoutController {

    private final ParkingLayoutService parkingLayoutService;
    private final ParkingService parkingService;
    private final LevelService levelService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LevelLayoutDto> getParkingLayout(@PathVariable(name = "id") UUID levelId) throws Exception {
        LevelLayoutDto levelLayoutDTO = parkingLayoutService.getLevelLayout(levelId);
        return ResponseEntity.ok(levelLayoutDTO);
    }
    
    @GetMapping("/nameListing/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LevelNameListingDto>> getParkingLevels(@PathVariable(name = "id") UUID parkingId) throws Exception {
        Parking parking = parkingService.getParkingById(parkingId)
                .orElseThrow(() -> new ParkingNotFoundException("Could not find parking with " + parkingId + " ID"));
        List<LevelNameListingDto> levelNameListingDtos = levelService.getNameListingFrom(parking);
        return ResponseEntity.ok(levelNameListingDtos);
    }
}
