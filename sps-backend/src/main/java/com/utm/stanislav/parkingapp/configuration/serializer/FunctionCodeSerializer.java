package com.utm.stanislav.parkingapp.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.utm.stanislav.parkingapp.enums.FunctionCode;

import java.io.IOException;

public class FunctionCodeSerializer extends JsonSerializer<FunctionCode> {
    
    @Override
    public void serialize(FunctionCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(String.valueOf(value.getCode()));
    }
}
