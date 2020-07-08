package com.utm.stanislav.parkingapp.fixture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.ParkingStatusJsonDeserializer;
import com.utm.stanislav.parkingapp.configuration.serializer.ParkingStatusJsonSerializer;
import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class ParkingLotFixture {
    
    private String logicalId;
    private Integer address;
    private User bookedBy;
    private RPiBridge rPiBridge;
    private Level level;
    private ParkingStatus parkingStatus;
    private Integer failedResponseCount;
    private ParkingLotPosition parkingLotPosition;
    private LayoutObject layoutObject;
    
    public static ParkingLotFixture withRPiBridge(RPiBridge rPiBridge) {
        return ParkingLotFixture.builder().rPiBridge(rPiBridge).build();
    }
    
    public ParkingLot get() {
        return new ParkingLot(logicalId, address, bookedBy, rPiBridge, level, parkingStatus,
                failedResponseCount, parkingLotPosition, layoutObject);
    }
}
