package com.utm.stanislav.parkingapp.service.rpibridge;

import com.utm.stanislav.parkingapp.model.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.repository.RPiBridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.Optional;

@Named
@RequiredArgsConstructor
public class RPiBridgeServiceImpl implements RPiBridgeService {
    
    private final RPiBridgeRepository rPiBridgeRepository;
    
    @Override
    @Transactional
    public Optional<RPiBridge> getBridgeByLogicalId(String logicalId) {
        return this.rPiBridgeRepository.findByLogicalId(logicalId);
    }
    
    @Override
    @Transactional
    public Optional<RPiBridge> getBridgeBySession(String sessionId) {
        return this.rPiBridgeRepository.findBySessionId(sessionId);
    }
    
    @Override
    @Transactional
    public void setSessionOnBridge(String logicalId, String sessionId) throws RPiBridgeNotFoundException {
        Optional<RPiBridge> rPiBridge = this.getBridgeByLogicalId(logicalId);
        if (rPiBridge.isPresent()) {
            rPiBridge.get().setSessionId(sessionId);
            rPiBridge.get().setIsConnected(Boolean.TRUE);
        } else {
            throw new RPiBridgeNotFoundException("Could not find rPiBridge by provided logicalId!");
        }
    }
    
    @Override
    @Transactional
    public void removeSessionFromBridge(String sessionId) throws RPiBridgeNotFoundException {
        Optional<RPiBridge> rPiBridge = this.getBridgeBySession(sessionId);
        if (rPiBridge.isPresent()) {
            rPiBridge.get().setSessionId(null);
            rPiBridge.get().setIsConnected(Boolean.FALSE);
        } else {
            throw new RPiBridgeNotFoundException("Could not find rPiBridge by provided session!");
        }
    }
}
