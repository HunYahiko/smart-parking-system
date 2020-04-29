package com.utm.stanislav.parkingapp.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;

import java.io.IOException;

public class ParkingStatusJsonSerializer extends JsonSerializer<ParkingStatus> {
    
    @Override
    public void serialize(ParkingStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
