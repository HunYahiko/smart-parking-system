package com.utm.stanislav.parkingapp.configuration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;

import java.io.IOException;
import java.util.Optional;

public class ParkingStatusJsonDeserializer extends JsonDeserializer<ParkingStatus> {
    
    @Override
    public ParkingStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
    
        String status = jsonNode.textValue();
        Optional<ParkingStatus> parkingStatus = ParkingStatus.fromString(status);
        if (parkingStatus.isPresent()) {
            return parkingStatus.get();
        } else {
            throw new IOException("Exception parsing MessageType");
        }
    }
}
