package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookRequestRepository extends JpaRepository<BookRequest, UUID> {

    Optional<BookRequest> findByParkingLot(ParkingLot parkingLot);
}
