package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.repository.ParkingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    
    private ParkingRepository parkingRepository;
    
    @Override
    public Optional<Parking> getParkingByName(String name) {
        return this.parkingRepository.findByLogicalId(name);
    }
}
