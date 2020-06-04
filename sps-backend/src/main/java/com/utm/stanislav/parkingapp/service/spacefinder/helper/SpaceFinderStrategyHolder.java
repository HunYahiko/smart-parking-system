package com.utm.stanislav.parkingapp.service.spacefinder.helper;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpaceFinderStrategyHolder {
    private final SpaceFinderStrategyType DEFAULT_STRATEGY = SpaceFinderStrategyType.PLAIN;
    public final static SpaceFinderStrategyHolder INSTANCE = new SpaceFinderStrategyHolder();
    
    private Map<UUID, SpaceFinderStrategyType> strategyTypesMap;
    
    private SpaceFinderStrategyHolder() {
        strategyTypesMap = new ConcurrentHashMap<>();
    }
    
    public SpaceFinderStrategyType getStrategy(UUID parkingId) {
        if (strategyTypesMap.containsKey(parkingId)) {
            return strategyTypesMap.get(parkingId);
        }
        strategyTypesMap.put(parkingId, DEFAULT_STRATEGY);
        return strategyTypesMap.get(parkingId);
    }
    
    public void setStrategy(UUID parkingId, SpaceFinderStrategyType type) {
        strategyTypesMap.put(parkingId, type);
    }
    
    public SpaceFinderStrategyType getDefaultStrategy() {
        return DEFAULT_STRATEGY;
    }
}
