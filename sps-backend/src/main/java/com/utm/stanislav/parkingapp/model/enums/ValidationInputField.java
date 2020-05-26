package com.utm.stanislav.parkingapp.model.enums;

public enum ValidationInputField {
    
    USERNAME("username"),
    PHONE_NUMBER("phoneNumber"),
    EMAIL("email");
    
    ValidationInputField(String name) {
        this.name = name;
    }
    
    private String name;
    
    public String getName() {
        return name;
    }
}
