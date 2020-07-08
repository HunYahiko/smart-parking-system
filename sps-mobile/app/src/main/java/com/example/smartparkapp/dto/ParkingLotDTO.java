package com.example.smartparkapp.dto;

public class ParkingLotDTO {
    private final String name;
    private final LevelDTO level;

    public ParkingLotDTO(String name, LevelDTO level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public LevelDTO getLevel() {
        return level;
    }
}
