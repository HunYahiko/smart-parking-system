package com.utm.stanislav.parkingapp.model.enums;

import java.util.Optional;

public enum FunctionCode {
    
    STATUS_CHECK(1);
    
    private int code;
    
    FunctionCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static Optional<FunctionCode> fromString(String jsonFunctionCode) {
        try {
            int code = Integer.parseInt(jsonFunctionCode);
            for (FunctionCode functionCode : FunctionCode.values()) {
                if (functionCode.getCode() == code) {
                    return Optional.of(functionCode);
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("Received function code is not an Integer!");
        }
        
        return Optional.empty();
    }
}
