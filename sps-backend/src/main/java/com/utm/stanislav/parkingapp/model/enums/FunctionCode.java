package com.utm.stanislav.parkingapp.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public enum FunctionCode {
    @JsonProperty("1")
    STATUS_CHECK(1),
    @JsonProperty("2")
    BLOCK_LOT(2),
    @JsonProperty("3")
    UNBLOCK_LOT(3);
    
    private int code;
    
    FunctionCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static Optional<FunctionCode> valueOfByCode(String jsonFunctionCode) {
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
