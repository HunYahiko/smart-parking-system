package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LevelRepository extends JpaRepository<Level, UUID> {
    
    List<Level> findAllByParking(Parking parking);
    Optional<Level> findFirstByParking(Parking parking);
}
