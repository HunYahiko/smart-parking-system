package com.utm.stanislav.parkingapp.model.enums;

public enum LayoutObjectOrientation {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right"),
    UPRIGHT("upright"),
    DOWNRIGHT("downright"),
    UPLEFT("UPLEFT"),
    DOWNLEFT("DOWNLEFT");
    
    private String orientation;
    
    LayoutObjectOrientation(String orientation) {
        this.orientation = orientation;
    }
    
    public String getOrientation() {
        return orientation;
    }
}
