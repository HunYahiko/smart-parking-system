package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.RPiBridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RPiBridgeRepository extends JpaRepository<RPiBridge, UUID> {
    
    Optional<RPiBridge> findByLogicalId(String logicalId);
    Optional<RPiBridge> findBySessionId(String sessionId);
}
