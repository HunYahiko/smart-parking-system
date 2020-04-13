package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
