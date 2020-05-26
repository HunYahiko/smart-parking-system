package com.utm.stanislav.parkingapp;

import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.model.enums.ChargingType;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectOrientation;
import com.utm.stanislav.parkingapp.model.enums.LayoutObjectType;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    
    @Inject
    private LevelLayoutRepository levelLayoutRepository;
    
    @Inject
    private LayoutObjectRepository layoutObjectRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(ParkingappApplication.class, args);
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        Coordinates coordinates = new Coordinates();
//        coordinates.setLatitude(47.016239D);
//        coordinates.setLongitude(28.837421D);
//        coordinates = this.coordinatesRepository.save(coordinates);
//
//        Address address = new Address();
//        address.setCoordinates(coordinates);
//        address.setStreetName("Strada Bulgară");
//        address.setStreetNumber("33/1");
//        address = this.addressRepository.save(address);
//
//        Parking parking = new Parking();
//        parking.setAddress(address);
//        parking.setLogicalId("ISD Parking");
//        parking.setChargingType(ChargingType.FREE_OF_CHARGE);
//        parking = this.parkingRepository.save(parking);
//
//        Level level = new Level();
//        level.setLogicalId("GROUND-LEVEL");
//        level.setParking(parking);
//        this.levelRepository.save(level);
//
//        RPiBridge rPiBridge = new RPiBridge();
//        rPiBridge.setLevel(level);
//        rPiBridge.setLogicalId("BRIDGE1-GROUNDLEVEL");
//        rPiBridge.setIsConnected(false);
//        rPiBridge.setSessionId(null);
//        this.rPiBridgeRepository.save(rPiBridge);
//
//        for(int i = 1; i < 11; ++i) {
//            ParkingLot parkingLot = new ParkingLot();
//            parking.setLogicalId("LOT" + i);
//            parkingLot.setAddress(i);
//            parkingLot.setFailedResponseCount(0);
//            parkingLot.setParkingStatus(ParkingStatus.UNRESPONSIVE);
//            parkingLot.setLevel(level);
//            parkingLot.setRPiBridge(rPiBridge);
//            parkingLot.setParkingLotPosition(null);
//            parkingLot.setBookedBy(null);
//            this.parkingLotRepository.save(parkingLot);
//        }
//
//        LevelLayout levelLayout = new LevelLayout();
//        levelLayout.setWidth(14);
//        levelLayout.setHeight(20);
//        levelLayout.setLevel(level);
//        this.levelLayoutRepository.save(levelLayout);
//
//        level.setLevelLayout(levelLayout);
//
//        List<LayoutObject> layoutObjects = new ArrayList<>();
//
//        for (int i = 4; i < 11; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.PARKING_LOT);
//            layoutObject.setOrientation(LayoutObjectOrientation.UP);
//            layoutObject.setXPosition(i);
//            layoutObject.setYPosition(0);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
//        for (int i = 7; i < 10; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.PARKING_LOT);
//            layoutObject.setOrientation(LayoutObjectOrientation.LEFT);
//            layoutObject.setXPosition(0);
//            layoutObject.setYPosition(i);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
//        for (int i = 2; i < 15; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.ROAD);
//            layoutObject.setOrientation(LayoutObjectOrientation.UP);
//            layoutObject.setXPosition(i);
//            layoutObject.setYPosition(1);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
//        for (int i = 1; i < 15; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.ROAD);
//            layoutObject.setOrientation(LayoutObjectOrientation.RIGHT);
//            layoutObject.setXPosition(1);
//            layoutObject.setYPosition(i);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
//        for (int i = 2; i < 15; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.ROAD);
//            layoutObject.setOrientation(LayoutObjectOrientation.DOWN);
//            layoutObject.setXPosition(i);
//            layoutObject.setYPosition(2);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
//        for (int i = 3; i < 15; ++i) {
//            LayoutObject layoutObject = new LayoutObject();
//            layoutObject.setType(LayoutObjectType.ROAD);
//            layoutObject.setOrientation(LayoutObjectOrientation.LEFT);
//            layoutObject.setXPosition(2);
//            layoutObject.setYPosition(i);
//            layoutObject.setLevelLayout(levelLayout);
//            this.layoutObjectRepository.save(layoutObject);
//        }
//
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
////
////        for (int i = 0; i < 3; ++i) {
////            Level level = new Level("LEVEL" + (i+1));
////            level.setParking(parking);
////            level = this.levelRepository.save(level);
////
////            int lotCounter = 0;
////            for (int j = 0; j < 15; ++j) {
////                RPiBridge rPiBridge = new RPiBridge("BRIDGE" + (j+1) + "-LEVEL" + (i+1), null, false);
////                rPiBridge.setLevel(level);
////                this.rPiBridgeRepository.save(rPiBridge);
////                for (int t = lotCounter; t < lotCounter + 4; ++t) {
////                    ParkingLot parkingLot = new ParkingLot("LOT" + (t+1), t - lotCounter + 1, ParkingStatus.FREE);
////                    parkingLot.setRPiBridge(rPiBridge);
////                    parkingLot.setLevel(level);
////                    parkingLot.setFailedResponseCount(0);
////                    this.saveParkingLot(parkingLot);
////                }
////                lotCounter += 4;
////            }
////        }
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
