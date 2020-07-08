package com.example.smartparkapp.dto;


import com.example.smartparkapp.model.enums.ChargingType;

public class ParkingLocationDTO {
    private final ParkingDTO parking;
    private final AddressDTO address;
    private final ChargingType chargingType;

    public ParkingLocationDTO(ParkingDTO parking, AddressDTO address, ChargingType chargingType) {
        this.parking = parking;
        this.address = address;
        this.chargingType = chargingType;
    }

    public ParkingDTO getParking() {
        return parking;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public ChargingType getChargingType() {
        return chargingType;
    }
}
