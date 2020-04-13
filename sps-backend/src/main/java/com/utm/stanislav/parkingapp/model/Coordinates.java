package com.utm.stanislav.parkingapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sps_coordinate")
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class Coordinates extends GenericEntity {
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
}
