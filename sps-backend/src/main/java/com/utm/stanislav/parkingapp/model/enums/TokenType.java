package com.utm.stanislav.parkingapp.model.enums;

public enum TokenType {
    
    BEARER("Bearer");
    
    private String type;
    
    TokenType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
