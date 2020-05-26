package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.LayoutObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LayoutObjectRepository extends JpaRepository<LayoutObject, UUID> {
}
