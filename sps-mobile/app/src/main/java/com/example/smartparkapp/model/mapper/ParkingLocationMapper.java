package com.example.smartparkapp.model.mapper;

import com.example.smartparkapp.dto.ParkingLocationDTO;
import com.example.smartparkapp.model.ParkingLocation;

import java.util.UUID;

public class ParkingLocationMapper implements Mapper<ParkingLocation, ParkingLocationDTO> {

    @Override
    public ParkingLocation map(ParkingLocationDTO object) {
        ParkingLocation parkingLocation = new ParkingLocation();
        parkingLocation.setParkingId(object.getParking().getId().toString());
        parkingLocation.setLatitude(object.getAddress().getCoordinates().getLatitude());
        parkingLocation.setLongitude(object.getAddress().getCoordinates().getLongitude());
        parkingLocation.setParkingName(object.getParking().getName());
        return parkingLocation;
    }
}
