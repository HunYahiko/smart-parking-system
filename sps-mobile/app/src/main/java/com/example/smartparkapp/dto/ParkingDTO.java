package com.example.smartparkapp.dto;

import java.util.UUID;

public class ParkingDTO {
    private final UUID id;
    private final String name;

    public ParkingDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
