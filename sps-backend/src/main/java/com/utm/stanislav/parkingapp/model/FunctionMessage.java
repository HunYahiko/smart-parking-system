package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.FunctionCodeJsonDeserializer;
import com.utm.stanislav.parkingapp.configuration.serializer.FunctionCodeJsonSerializer;
import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.inject.Named;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Named
public class FunctionMessage extends Message {
    
    @JsonSerialize(using = FunctionCodeJsonSerializer.class)
    @JsonDeserialize(using = FunctionCodeJsonDeserializer.class)
    private FunctionCode functionCode;
    
    private UUID parkingLotId;
}
