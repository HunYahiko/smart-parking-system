package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRequestRepository extends JpaRepository<BookRequest, UUID> {

    Optional<BookRequest> findByParkingLot(ParkingLot parkingLot);
    List<BookRequest> findAllByUser(User user);
}
