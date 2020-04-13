package com.utm.stanislav.parkingapp.service.parkinglot;

import com.utm.stanislav.parkingapp.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.repository.ParkingLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    
    private ParkingLotRepository parkingLotRepository;
    
    @Inject
    public void setParkingLotRepository(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ParkingLot> getAllLotsPairedWith(RPiBridge rPiBridge) {
        return this.parkingLotRepository.findAllByrPiBridge(rPiBridge);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<ParkingLot> getFreeRandomParkingLotFrom(Level level) {
        List<ParkingLot> parkingLots =
                this.parkingLotRepository.findByLevelAndParkingStatus(level, ParkingStatus.FREE);
        parkingLots = this.filterByFailCounter(parkingLots);
        if (parkingLots.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parkingLots.get(0));
    }
}
