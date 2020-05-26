package com.utm.stanislav.parkingapp.model.enums;

import java.util.Optional;

public enum ParkingStatus {
    FREE("FREE", 0),
    OCCUPIED("OCCUPIED", 1),
    BOOKED("BOOKED", 2),
    UNRESPONSIVE("UNRESPONSIVE", 3);
    
    private String name;
    private int code;
    
    ParkingStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public int getCode() {
        return code;
    }
    
    public static Optional<ParkingStatus> fromString(String status) {
        for (ParkingStatus parkingStatus: values()) {
            if (parkingStatus.getName().equals(status)) {
                return Optional.of(parkingStatus);
            }
        }
        return Optional.empty();
    }
    
    public static Optional<ParkingStatus> valueOfByCode(int code) {
        for (ParkingStatus parkingStatus : values()) {
            if (parkingStatus.getCode() == code) {
                return Optional.of(parkingStatus);
            }
        }
        return Optional.empty();
    }
}
