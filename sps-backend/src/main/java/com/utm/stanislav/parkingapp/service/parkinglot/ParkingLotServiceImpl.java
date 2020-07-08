package com.utm.stanislav.parkingapp.service.parkinglot;

import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {
    
    private final ParkingLotRepository parkingLotRepository;
    
    @Override
    @Transactional
    public List<ParkingLot> getAllPairedWith(RPiBridge rPiBridge) {
        return this.parkingLotRepository.findAllByrPiBridge(rPiBridge);
    }
    
    @Override
    @Transactional
    public Optional<ParkingLot> getFreeRandomFrom(Level level) {
        List<ParkingLot> parkingLots =
                this.parkingLotRepository.findByLevelAndParkingStatus(level, ParkingStatus.FREE);
        parkingLots = this.filterByFailCounter(parkingLots);
        if (parkingLots.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parkingLots.get(0));
    }
    
    @Override
    @Transactional
    public List<ParkingLot> getAllFrom(Parking parking) {
        return parkingLotRepository.findAllByParkingId(parking.getId());
    }
    
    @Override
    public Optional<ParkingLot> getById(UUID parkingLotId) {
        return parkingLotRepository.findById(parkingLotId);
    }
    
    @Override
    public List<ParkingLot> getAll() {
        return parkingLotRepository.findAll();
    }
    
    @Override
    public Optional<ParkingLot> getOneFreeRandomFrom(Parking parking) {
        return parkingLotRepository.findOneFreeRandomFrom(parking.getId());
    }
}
