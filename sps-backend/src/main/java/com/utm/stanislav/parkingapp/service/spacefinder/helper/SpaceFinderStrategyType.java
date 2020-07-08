package com.utm.stanislav.parkingapp.service.spacefinder.helper;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum SpaceFinderStrategyType {
    
    PLAIN("Plain"),
    LEVELED_DISTRIBUTION("Leveled Distribution");
    
    private String type;
    
    SpaceFinderStrategyType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public static SpaceFinderStrategyType valueOfByType(String type) {
        return Arrays.stream(values())
                .filter(strategyType -> strategyType.type.equals(type))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
