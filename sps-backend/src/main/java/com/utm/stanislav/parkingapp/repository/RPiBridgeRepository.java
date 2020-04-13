package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.RPiBridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RPiBridgeRepository extends JpaRepository<RPiBridge, Long> {
    
    Optional<RPiBridge> findByLogicalId(String logicalId);
    Optional<RPiBridge> findBySessionId(String sessionId);
}
