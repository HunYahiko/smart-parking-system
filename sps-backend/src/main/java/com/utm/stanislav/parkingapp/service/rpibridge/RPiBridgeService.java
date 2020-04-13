package com.utm.stanislav.parkingapp.service.rpibridge;

import com.utm.stanislav.parkingapp.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.model.RPiBridge;

import java.util.Optional;

public interface RPiBridgeService {
    
    Optional<RPiBridge> getBridgeByLogicalId(String logicalId);
    Optional<RPiBridge> getBridgeBySession(String sessionId);
    void setSessionOnBridge(String logicalId, String sessionId) throws RPiBridgeNotFoundException;
    void removeSessionFromBridge(String sessionId) throws RPiBridgeNotFoundException;
}
