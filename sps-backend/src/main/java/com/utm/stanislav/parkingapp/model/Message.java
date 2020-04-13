package com.utm.stanislav.parkingapp.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utm.stanislav.parkingapp.configuration.deserializer.MessageTypeDeserializer;
import com.utm.stanislav.parkingapp.configuration.serializer.MessageTypeSerializer;
import com.utm.stanislav.parkingapp.enums.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Message extends GenericEntity {
    
    @Column(name = "address")
    private Integer address;
    
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    @JsonDeserialize(using = MessageTypeDeserializer.class)
    @JsonSerialize(using = MessageTypeSerializer.class)
    private MessageType messageType;
    
    public Message(Integer address, MessageType messageType) {
        this.address = address;
        this.messageType = messageType;
    }
    
    public Message() {}
}
