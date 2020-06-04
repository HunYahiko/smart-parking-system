package com.utm.stanislav.parkingapp.service.parkinglot;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ParkingLotService {
    
    List<ParkingLot> getAllPairedWith(RPiBridge rPiBridge);
    Optional<ParkingLot> getFreeRandomFrom(Level level);
    Optional<ParkingLot> getOneFreeRandomFrom(Parking parking);
    default List<ParkingLot> filterByFailCounter(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                       .filter(parkingLot -> parkingLot.getFailedResponseCount() == 0)
                       .collect(Collectors.toList());
    }
    List<ParkingLot> getAllFrom(Parking parking);
    Optional<ParkingLot> getById(UUID parkingLotId);
    List<ParkingLot> getAll();
}
