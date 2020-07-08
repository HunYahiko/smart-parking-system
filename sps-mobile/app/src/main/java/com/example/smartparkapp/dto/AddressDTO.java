package com.example.smartparkapp.dto;

import com.example.smartparkapp.model.Coordinates;

public class AddressDTO {
    private final String streetName;
    private final String streetNumber;
    private final Coordinates coordinates;

    public AddressDTO(String streetName, String streetNumber, Coordinates coordinates) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.coordinates = coordinates;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
