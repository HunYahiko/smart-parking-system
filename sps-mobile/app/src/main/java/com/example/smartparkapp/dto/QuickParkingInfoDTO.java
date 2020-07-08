package com.example.smartparkapp.dto;

import com.example.smartparkapp.model.enums.ChargingType;

public class QuickParkingInfoDTO {
    private final ParkingDTO parking;
    private final AddressDTO address;
    private final Long totalParkingLots;
    private final Long freeParkingLots;
    private final ChargingType chargingType;

    public QuickParkingInfoDTO(ParkingDTO parking, AddressDTO address,
                               Long totalParkingLots, Long freeParkingLots, ChargingType chargingType) {
        this.parking = parking;
        this.address = address;
        this.totalParkingLots = totalParkingLots;
        this.freeParkingLots = freeParkingLots;
        this.chargingType = chargingType;
    }

    public ParkingDTO getParking() {
        return parking;
    }

    public Long getTotalParkingLots() {
        return totalParkingLots;
    }

    public Long getFreeParkingLots() {
        return freeParkingLots;
    }

    public ChargingType getChargingType() {
        return chargingType;
    }

    public AddressDTO getAddress() {
        return address;
    }
}
