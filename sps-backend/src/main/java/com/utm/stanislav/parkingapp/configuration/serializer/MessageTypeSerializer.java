package com.utm.stanislav.parkingapp.configuration.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.utm.stanislav.parkingapp.enums.MessageType;

import java.io.IOException;

public class MessageTypeSerializer extends JsonSerializer<MessageType> {
    
    @Override
    public void serialize(MessageType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
