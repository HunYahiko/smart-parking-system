package com.utm.stanislav.parkingapp.configuration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.utm.stanislav.parkingapp.enums.FunctionCode;

import java.io.IOException;
import java.util.Optional;

public class FunctionCodeDeserializer extends JsonDeserializer<FunctionCode> {
    
    @Override
    public FunctionCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode jsonNode = p.getCodec().readTree(p);
        
        String jsonFunctionCode = jsonNode.textValue();
        Optional<FunctionCode> functionCode = FunctionCode.fromString(jsonFunctionCode);
        if (functionCode.isPresent()) {
            return functionCode.get();
        }
        throw new IOException("FunctionCode is not a valid entry. Failed to deserialize");
    }
}
