package com.example.smartparkapp.model.enums;

public enum ValidationInputField {

    USERNAME("username"),
    PHONE_NUMBER("phone_number");

    ValidationInputField(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}