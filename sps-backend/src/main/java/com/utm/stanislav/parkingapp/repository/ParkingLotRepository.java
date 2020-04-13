package com.utm.stanislav.parkingapp.repository;

import com.utm.stanislav.parkingapp.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findAllByrPiBridge(RPiBridge rPiBridge);
    Optional<ParkingLot> findFirstByLevelAndParkingStatus(Level level, ParkingStatus parkingStatus);
    List<ParkingLot> findByLevelAndParkingStatus(Level level, ParkingStatus parkingStatus);
}
