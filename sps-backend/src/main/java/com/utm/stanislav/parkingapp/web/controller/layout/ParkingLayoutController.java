package com.utm.stanislav.parkingapp.web.controller.layout;

import com.utm.stanislav.parkingapp.service.layout.ParkingLayoutService;
import com.utm.stanislav.parkingapp.web.dto.LevelLayoutDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLayoutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/layouts")
@RequiredArgsConstructor
public class ParkingLayoutController {

    private final ParkingLayoutService parkingLayoutService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LevelLayoutDTO> getParkingLayout(@PathVariable(name = "id") UUID levelId) throws Exception {
        LevelLayoutDTO levelLayoutDTO = parkingLayoutService.getLevelLayout(levelId);
        return ResponseEntity.ok(levelLayoutDTO);
    }
}
