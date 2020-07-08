package com.example.smartparkapp.dto;

import com.example.smartparkapp.model.enums.BookRequestStatus;

import java.util.UUID;

public class BookRequestDto {
    private UUID id;
    private String parking;
    private String parkingLot;
    private BookRequestStatus bookRequestStatus;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(String parkingLot) {
        this.parkingLot = parkingLot;
    }

    public BookRequestStatus getBookRequestStatus() {
        return bookRequestStatus;
    }

    public void setBookRequestStatus(BookRequestStatus bookRequestStatus) {
        this.bookRequestStatus = bookRequestStatus;
    }
}
