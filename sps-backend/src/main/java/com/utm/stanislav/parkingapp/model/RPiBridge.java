package com.utm.stanislav.parkingapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_rpi_bridge")
@Data
public class RPiBridge extends GenericEntity{
    
    @Column(name = "logical_id")
    private String logicalId;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @Column(name = "isConnected")
    private Boolean isConnected;
    
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;
    
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "rPiBridge")
    List<ParkingLot> parkingLots;
    
    public RPiBridge() {}
    
    public RPiBridge(String logicalId, String sessionId, Boolean isConnected) {
        this.logicalId = logicalId;
        this.sessionId = sessionId;
        this.isConnected = isConnected;
    }
}
