package com.utm.stanislav.parkingapp.model.enums;

public enum ChargingType {
    FREE_OF_CHARGE("FREE_OF_CHARGE"),
    ON_BOOKING_CHARGE("BOOKING_CHARGE"),
    BILLABLE("USAGE_CHARGE");
    
    ChargingType(String name) {
        this.name = name;
    }
    
    private String name;
    
    public String getName() {
        return name;
    }
}
