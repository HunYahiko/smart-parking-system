package com.utm.stanislav.parkingapp;

import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@SpringBootApplication
public class ParkingappApplication implements CommandLineRunner {
    
    @Inject
    private LevelRepository levelRepository;
    
    @Inject
    private ParkingLotRepository parkingLotRepository;
    
    @Inject
    private RPiBridgeRepository rPiBridgeRepository;
    
    @Inject
    private CoordinatesRepository coordinatesRepository;
    
    @Inject
    private AddressRepository addressRepository;
    
    @Inject
    private ParkingRepository parkingRepository;
    
    @Inject
    private RoleRepository roleRepository;
    
    @Inject
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(ParkingappApplication.class, args);
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        Coordinates coordinates = new Coordinates();
//        coordinates.setLatitude(2.4D);
//        coordinates.setLongitude(3.5D);
//        coordinates = this.coordinatesRepository.save(coordinates);
//
//        Address address = new Address();
//        address.setCoordinates(coordinates);
//        address.setStreetName("Bd.Decebal");
//        address.setStreetNumber("139");
//        address = this.addressRepository.save(address);
//
//        Parking parking = new Parking();
//        parking.setAddress(address);
//        parking.setLogicalId("DECEBAL_PARKING");
//        parking = this.parkingRepository.save(parking);
//
////        Level level = new Level("LEVEL1");
////        level.setParking(parking);
////        this.saveLevel(level);
////
////        for (int i = 2; i < 17; ++i) {
////            Level newLevel = new Level("LEVEL" + i);
////            newLevel.setParking(parking);
////            this.saveLevel(newLevel);
////        }
//
//        for (int i = 0; i < 3; ++i) {
//            Level level = new Level("LEVEL" + (i+1));
//            level.setParking(parking);
//            level = this.levelRepository.save(level);
//
//            int lotCounter = 0;
//            for (int j = 0; j < 15; ++j) {
//                RPiBridge rPiBridge = new RPiBridge("BRIDGE" + (j+1) + "-LEVEL" + (i+1), null, false);
//                rPiBridge.setLevel(level);
//                this.rPiBridgeRepository.save(rPiBridge);
//                for (int t = lotCounter; t < lotCounter + 4; ++t) {
//                    ParkingLot parkingLot = new ParkingLot("LOT" + (t+1), t - lotCounter + 1, ParkingStatus.FREE);
//                    parkingLot.setRPiBridge(rPiBridge);
//                    parkingLot.setLevel(level);
//                    parkingLot.setFailedResponseCount(0);
//                    this.saveParkingLot(parkingLot);
//                }
//                lotCounter += 4;
//            }
//        }
    }
    
    @Transactional
    protected void saveLevel(Level level) {
        this.levelRepository.save(level);
    }
    
    @Transactional
    protected void saveRPiBridge(RPiBridge rPiBridge) {
        this.rPiBridgeRepository.save(rPiBridge);
    }
    
    @Transactional
    protected void saveParkingLot(ParkingLot parkingLot) {
        this.parkingLotRepository.save(parkingLot);
    }
    
}
