package com.utm.stanislav.parkingapp.service.spacefinder.helper;

import com.utm.stanislav.parkingapp.model.exceptions.SpaceFinderException;
import com.utm.stanislav.parkingapp.model.exceptions.StrategyResolverException;
import com.utm.stanislav.parkingapp.service.spacefinder.strategy.SpaceFinderStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@RequiredArgsConstructor
public class SpaceFinderStrategyResolver {
    
    private final List<SpaceFinderStrategy> spaceFinderStrategies;
    
    public SpaceFinderStrategy resolve(SpaceFinderStrategyType type) throws StrategyResolverException {
        return spaceFinderStrategies.stream()
                .filter(spaceFinderStrategy -> spaceFinderStrategy.getType().getType().equals(type.getType()))
                .findAny()
                .orElse(this.getDefaultStrategy());
    }
    
    private SpaceFinderStrategy getDefaultStrategy() throws StrategyResolverException {
        return spaceFinderStrategies.stream()
                .filter(spaceFinderStrategy -> spaceFinderStrategy.getType().getType().equals(SpaceFinderStrategyHolder.INSTANCE.getDefaultStrategy().getType()))
                .findAny()
                .orElseThrow(() -> new StrategyResolverException("Could not resolve a default implementation for SpaceFinderStrategy!"));
    }
}
