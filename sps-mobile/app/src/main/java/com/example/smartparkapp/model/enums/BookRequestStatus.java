package com.example.smartparkapp.model.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum BookRequestStatus {
    AWAITING_CONFIRMATION(0, "Awaiting Confirm"),
    CONFIRMED(1, "Confirmed"),
    FINISHED(2, "Finished"),
    FAILED(3, "Failed");

    private int code;
    private String status;

    BookRequestStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public static BookRequestStatus valueOfByCode(int code) throws Throwable {
        return Arrays.stream(values())
                .filter(bookRequestStatus -> bookRequestStatus.code == code)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
