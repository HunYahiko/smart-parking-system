package com.utm.stanislav.parkingapp.service.parking;

import com.utm.stanislav.parkingapp.model.Address;
import com.utm.stanislav.parkingapp.model.Parking;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import com.utm.stanislav.parkingapp.model.exceptions.ParkingNotFoundException;
import com.utm.stanislav.parkingapp.repository.ParkingRepository;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import com.utm.stanislav.parkingapp.web.dto.AddressDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingLocationDto;
import com.utm.stanislav.parkingapp.web.dto.QuickParkingInfoDto;
import lombok.RequiredArgsConstructor;
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
    public List<ParkingLocationDto> fetchParkingsLocations() {
        List<Parking> parkings = parkingRepository.findAll();
        return parkings.stream()
                .map(this::mapParking)
                .collect(Collectors.toList());
    }
    
    private ParkingLocationDto mapParking(Parking parking) {
        ParkingDto parkingDTO = new ParkingDto(parking.getId(), parking.getLogicalId());
        Address parkingAddress = parking.getAddress();
        AddressDto addressDTO = new AddressDto(parkingAddress.getStreetName(),
                                                parkingAddress.getStreetNumber(),
                                                parkingAddress.getCoordinates());
        return new ParkingLocationDto(parkingDTO, addressDTO, parking.getChargingType());
    }
    
    @Override
    @Transactional
    public QuickParkingInfoDto getQuickParkingInfo(UUID parkingId) throws ParkingNotFoundException{
        Parking parking = parkingRepository.findById(parkingId)
                                                    .orElseThrow(ParkingNotFoundException::new);
        
        List<ParkingLot> parkingLots = parkingLotService.getAllFrom(parking);
        Long freeParkingLots = parkingLots.stream().parallel()
                                                .filter(ParkingLot::isFree)
                                                .count();
        Long totalParkingLots = (long) parkingLots.size();
        ChargingType parkingChargingType = parking.getChargingType();
        
        ParkingDto parkingDTO = new ParkingDto(parkingId, parking.getLogicalId());
        
        Address address = parking.getAddress();
        AddressDto addressDTO = new AddressDto(address.getStreetName(), address.getStreetNumber(), address.getCoordinates());
        
        return new QuickParkingInfoDto(parkingDTO, addressDTO, totalParkingLots, freeParkingLots, parkingChargingType);
        
    }
}
