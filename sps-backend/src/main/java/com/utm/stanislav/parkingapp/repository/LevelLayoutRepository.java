package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.LevelLayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LevelLayoutRepository extends JpaRepository<LevelLayout, UUID> {
}
