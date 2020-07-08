package com.utm.stanislav.parkingapp.fixture;

import com.utm.stanislav.parkingapp.model.Level;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class RPiBridgeFixture {
    
    private String logicalId;
    private String sessionId;
    private Boolean isConnected;
    private Level level;
    List<ParkingLot> parkingLots;
    
    public static RPiBridgeFixture withIsConnectedAndSessionId(String sessionId) {
        return RPiBridgeFixture.builder().isConnected(true).sessionId(sessionId).build();
    }
    
    public RPiBridge get() {
        return new RPiBridge(logicalId, sessionId, isConnected, level, parkingLots);
    }
}
