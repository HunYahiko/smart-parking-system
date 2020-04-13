package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "sps_address")
@Data
@EqualsAndHashCode(callSuper = false)
public class Address extends GenericEntity {
    
    @Column(name = "street_name")
    private String streetName;
    
    @Column(name = "street_number")
    private String streetNumber;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", nullable = false)
    @JsonBackReference
    private Coordinates coordinates;
}
