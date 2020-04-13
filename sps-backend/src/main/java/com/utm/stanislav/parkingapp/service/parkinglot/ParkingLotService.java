package com.utm.stanislav.parkingapp.service.parkinglot;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ParkingLotService {
    
    List<ParkingLot> getAllLotsPairedWith(RPiBridge rPiBridge);
    Optional<ParkingLot> getFreeRandomParkingLotFrom(Level level);
    default List<ParkingLot> filterByFailCounter(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                       .filter(parkingLot -> parkingLot.getFailedResponseCount() == 0)
                       .collect(Collectors.toList());
    }
}
