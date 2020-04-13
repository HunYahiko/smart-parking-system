package com.utm.stanislav.parkingapp.service.rpibridge;

import com.utm.stanislav.parkingapp.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.repository.RPiBridgeRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named
public class RPiBridgeServiceImpl implements RPiBridgeService {
    
    private RPiBridgeRepository rPiBridgeRepository;
    
    @Inject
    public void setrPiBridgeRepository(RPiBridgeRepository rPiBridgeRepository) {
        this.rPiBridgeRepository = rPiBridgeRepository;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<RPiBridge> getBridgeByLogicalId(String logicalId) {
        return this.rPiBridgeRepository.findByLogicalId(logicalId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<RPiBridge> getBridgeBySession(String sessionId) {
        return this.rPiBridgeRepository.findBySessionId(sessionId);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
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
