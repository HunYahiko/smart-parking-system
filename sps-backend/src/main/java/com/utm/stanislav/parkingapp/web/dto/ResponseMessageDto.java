package com.utm.stanislav.parkingapp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.FunctionCodeJsonDeserializer;
import com.utm.stanislav.parkingapp.configuration.deserializer.MessageTypeJsonDeserializer;
import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.enums.MessageType;
import com.utm.stanislav.parkingapp.model.enums.ResponseStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ResponseMessageDto {
    
    private final UUID id;
    private final Integer address;
    
    @JsonProperty("messageType")
    @JsonDeserialize(using = MessageTypeJsonDeserializer.class)
    private final MessageType messageType;
    
    @JsonProperty("functionCode")
    @JsonDeserialize(using = FunctionCodeJsonDeserializer.class)
    private final FunctionCode functionCode;
    
    private final ResponseStatus responseStatus;
    private final String responseData;
    private final UUID parkingLotId;
}
