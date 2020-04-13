package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LevelRepository extends JpaRepository<Level, Long> {
    
    List<Level> findAllByParking(Parking parking);
    Level findFirstByParking(Parking parking);
}
