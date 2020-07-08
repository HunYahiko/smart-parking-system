package com.utm.stanislav.parkingapp.service.rpibridge;

import com.utm.stanislav.parkingapp.model.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.model.RPiBridge;

import java.util.Optional;

public interface RPiBridgeService {
    
    Optional<RPiBridge> getByLogicalId(String logicalId);
    Optional<RPiBridge> getBySessionId(String sessionId);
    void setSessionIdOn(String logicalId, String sessionId) throws RPiBridgeNotFoundException;
    void removeSessionIdFrom(String sessionId) throws RPiBridgeNotFoundException;
}
