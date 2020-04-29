package com.utm.stanislav.parkingapp.model.enums;

import java.util.Optional;

public enum ParkingStatus {
    FREE("FREE"),
    OCCUPIED("OCCUPIED"),
    BOOKED("BOOKED"),
    UNRESPONSIVE("UNRESPONSIVE");
    
    private String name;
    
    ParkingStatus(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static Optional<ParkingStatus> fromString(String status) {
        for (ParkingStatus parkingStatus: ParkingStatus.values()) {
            if (parkingStatus.getName().equals(status)) {
                return Optional.of(parkingStatus);
            }
        }
        return Optional.empty();
    }
}
