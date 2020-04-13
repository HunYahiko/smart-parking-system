package com.utm.stanislav.parkingapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_parking")
@Data
public class Parking extends GenericEntity {
    
    @Column(name = "logical_id")
    private String logicalId;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parking")
    private List<Level> levels;
}
