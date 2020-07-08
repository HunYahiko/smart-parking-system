package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.ParkingStatusJsonDeserializer;
import com.utm.stanislav.parkingapp.configuration.serializer.ParkingStatusJsonSerializer;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "sps_parking_lot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot extends GenericEntity {
    
    @Column(name = "logical_id")
    private String logicalId;
    
    @Column(name = "address")
    private Integer address;
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User bookedBy;
    
    @ManyToOne
    @JoinColumn(name = "bridge_id", nullable = false)
    private RPiBridge rPiBridge;
    
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;
    
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    @JsonDeserialize(using = ParkingStatusJsonDeserializer.class)
    @JsonSerialize(using = ParkingStatusJsonSerializer.class)
    private ParkingStatus parkingStatus;
    
    @JsonIgnore
    @Column(name = "failed_response_count")
    private Integer failedResponseCount;
    
    @OneToOne
    @JoinColumn(name = "position")
    private ParkingLotPosition parkingLotPosition;
    
    @OneToOne
    @JoinColumn(name = "layout_object_id", nullable = false)
    private LayoutObject layoutObject;
    
    public ParkingLot(String logicalId, Integer address, ParkingStatus parkingStatus) {
        this.logicalId = logicalId;
        this.address = address;
        this.parkingStatus = parkingStatus;
    }
    
    public boolean isFree() {
        return parkingStatus.equals(ParkingStatus.FREE);
    }
}
