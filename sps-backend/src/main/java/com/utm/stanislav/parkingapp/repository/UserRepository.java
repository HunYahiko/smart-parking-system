package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByUsername(String username);
}
