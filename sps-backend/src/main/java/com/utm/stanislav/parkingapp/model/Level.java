package com.utm.stanislav.parkingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sps_level")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@RequiredArgsConstructor
public class Level extends GenericEntity {
    
    @Column(name = "logical_id")
    private String logicalId;
    
    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;
    
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "level")
    private List<RPiBridge> rPiBridge;
    
    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "level")
    private List<ParkingLot> parkingLots;
    
    @OneToOne
    @JoinColumn(name = "level_layout_id")
    private LevelLayout levelLayout;
    
    public Level(String logicalId) {
        this.logicalId = logicalId;
    }
}
