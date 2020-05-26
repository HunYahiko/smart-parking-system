package com.utm.stanislav.parkingapp.model;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_parking_lot_position")
@Data
@NoArgsConstructor
public class ParkingLotPosition extends GenericEntity {
    
    @Column(name = "x_position")
    private Integer xPosition;
    
    @Column(name = "yPosition")
    private Integer yPosition;
}
