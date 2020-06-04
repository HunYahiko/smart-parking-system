package com.utm.stanislav.parkingapp.model.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum BookRequestStatus {
    AWAITING_CONFIRMATION(0),
    CONFIRMED(1),
    FINISHED(2),
    FAILED(3);
    
    private int code;
    
    BookRequestStatus(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public static BookRequestStatus valueOfByCode(int code) {
        return Arrays.stream(values())
                .filter(bookRequestStatus -> bookRequestStatus.code == code)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
