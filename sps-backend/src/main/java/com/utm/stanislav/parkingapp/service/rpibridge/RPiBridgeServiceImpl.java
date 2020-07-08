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
    public Optional<RPiBridge> getByLogicalId(String logicalId) {
        return this.rPiBridgeRepository.findByLogicalId(logicalId);
    }
    
    @Override
    @Transactional
    public Optional<RPiBridge> getBySessionId(String sessionId) {
        return this.rPiBridgeRepository.findBySessionId(sessionId);
    }
    
    @Override
    @Transactional
    public void setSessionIdOn(String logicalId, String sessionId) throws RPiBridgeNotFoundException {
        RPiBridge rPiBridge = getByLogicalId(logicalId)
                .orElseThrow(() -> new RPiBridgeNotFoundException("Could not find rPiBridge by provided logicalId!"));
        rPiBridge.setSessionId(sessionId);
        rPiBridge.setIsConnected(Boolean.TRUE);
    }
    
    @Override
    @Transactional
    public void removeSessionIdFrom(String sessionId) throws RPiBridgeNotFoundException {
        RPiBridge rPiBridge = getBySessionId(sessionId)
                .orElseThrow(() -> new RPiBridgeNotFoundException("Could not find rPiBridge by provided session!"));
        rPiBridge.setSessionId(null);
        rPiBridge.setIsConnected(Boolean.FALSE);

    }
}
