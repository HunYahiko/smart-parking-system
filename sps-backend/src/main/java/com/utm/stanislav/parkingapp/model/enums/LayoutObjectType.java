package com.utm.stanislav.parkingapp.model.enums;

import com.utm.stanislav.parkingapp.model.LayoutObject;

public enum  LayoutObjectType {
    PARKING_LOT("parking_lot"),
    ROAD("road");
    
    private String type;
    
    LayoutObjectType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
