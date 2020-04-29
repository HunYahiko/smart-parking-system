package com.utm.stanislav.parkingapp.model.enums;

import java.util.Optional;

public enum MessageType {
    FUNCTION_MESSAGE("FUNC"),
    RESPONSE_MESSAGE("RESP");
    
    private String name;
    
    MessageType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public static Optional<MessageType> fromString(String type) {
        for (MessageType messageType: MessageType.values()) {
            if (messageType.getName().equals(type)) {
                return Optional.of(messageType);
            }
        }
        return Optional.empty();
    }
}
