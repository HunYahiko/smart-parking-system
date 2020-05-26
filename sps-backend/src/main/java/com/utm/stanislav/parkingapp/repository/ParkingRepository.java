package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {
    
    Optional<Parking> findByLogicalId(String logicalId);
}
