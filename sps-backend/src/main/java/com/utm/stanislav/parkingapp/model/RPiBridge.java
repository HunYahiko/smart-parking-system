package com.utm.stanislav.parkingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_rpi_bridge")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    public RPiBridge(String logicalId, String sessionId, Boolean isConnected) {
        this.logicalId = logicalId;
        this.sessionId = sessionId;
        this.isConnected = isConnected;
    }
}
