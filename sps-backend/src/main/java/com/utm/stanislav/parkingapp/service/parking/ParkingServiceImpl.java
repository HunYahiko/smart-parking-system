package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Address;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.repository.ParkingRepository;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import com.utm.stanislav.parkingapp.web.dto.AddressDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLocationDTO;
import com.utm.stanislav.parkingapp.web.dto.QuickParkingInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {
    
    private final ParkingRepository parkingRepository;
    private final ParkingLotService parkingLotService;
    
    @Override
    public Optional<Parking> getParkingByName(String name) {
        return this.parkingRepository.findByLogicalId(name);
    }
    
    @Override
    public Optional<Parking> getParkingById(UUID uuid) {
        return parkingRepository.findById(uuid);
    }
    
    @Override
    @Transactional
    public List<ParkingLocationDTO> fetchParkingsLocations() {
        List<Parking> parkings = parkingRepository.findAll();
        return parkings.stream()
                .map(this::mapParking)
                .collect(Collectors.toList());
    }
    
    private ParkingLocationDTO mapParking(Parking parking) {
        ParkingDTO parkingDTO = new ParkingDTO(parking.getId(), parking.getLogicalId());
        Address parkingAddress = parking.getAddress();
        AddressDTO addressDTO = new AddressDTO(parkingAddress.getStreetName(),
                                                parkingAddress.getStreetNumber(),
                                                parkingAddress.getCoordinates());
        return new ParkingLocationDTO(parkingDTO, addressDTO, parking.getChargingType());
    }
    
    @Override
    @Transactional
    public QuickParkingInfoDTO getQuickParkingInfo(UUID parkingId) throws ParkingNotFoundException{
        Parking parking = parkingRepository.findById(parkingId)
                                                    .orElseThrow(ParkingNotFoundException::new);
        
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLots(parking);
        Long freeParkingLots = parkingLots.stream().parallel()
                                                .filter(ParkingLot::isFree)
                                                .count();
        Long totalParkingLots = (long) parkingLots.size();
        ChargingType parkingChargingType = parking.getChargingType();
        
        ParkingDTO parkingDTO = new ParkingDTO(parkingId, parking.getLogicalId());
        
        Address address = parking.getAddress();
        AddressDTO addressDTO = new AddressDTO(address.getStreetName(), address.getStreetNumber(), address.getCoordinates());
        
        return new QuickParkingInfoDTO(parkingDTO, addressDTO, totalParkingLots, freeParkingLots, parkingChargingType);
        
    }
}
