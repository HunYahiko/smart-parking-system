package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {
    
    Optional<Parking> findByLogicalId(String logicalId);
}
