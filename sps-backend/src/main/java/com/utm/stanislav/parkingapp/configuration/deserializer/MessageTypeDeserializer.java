package com.utm.stanislav.parkingapp.configuration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utm.stanislav.parkingapp.enums.MessageType;

import java.io.IOException;
import java.util.Optional;

public class MessageTypeDeserializer extends JsonDeserializer<MessageType> {
    
    
    @Override
    public MessageType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        
        String type = jsonNode.textValue();
        Optional<MessageType> messageType = MessageType.fromString(type);
        if (messageType.isPresent()) {
            return messageType.get();
        } else {
            throw new IOException("Exception parsing MessageType");
        }
    }
}
