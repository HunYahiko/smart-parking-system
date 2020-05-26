package com.utm.stanislav.parkingapp.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ResponseStatus {
    @JsonProperty("OK")
    OK("OK"),
    
    @JsonProperty("UNRESPONSIVE")
    UNRESPONSIVE("UNRESPONSIVE");
    
    private String name;
    
    ResponseStatus(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
