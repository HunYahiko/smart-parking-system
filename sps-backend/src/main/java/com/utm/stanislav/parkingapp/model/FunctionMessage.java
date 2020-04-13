package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.FunctionCodeDeserializer;
import com.utm.stanislav.parkingapp.configuration.serializer.FunctionCodeSerializer;
import com.utm.stanislav.parkingapp.enums.FunctionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.inject.Named;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Named
public class FunctionMessage extends Message {
    
    @JsonSerialize(using = FunctionCodeSerializer.class)
    @JsonDeserialize(using = FunctionCodeDeserializer.class)
    private FunctionCode functionCode;
    
    private UUID parkingLotId;
}
