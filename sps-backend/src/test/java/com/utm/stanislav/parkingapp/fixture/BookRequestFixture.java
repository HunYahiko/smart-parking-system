package com.utm.stanislav.parkingapp.fixture;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.enums.BookRequestStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestFixture {
    private ParkingLot parkingLot;
    private User user;
    private Integer failedAttempts;
    private BookRequestStatus bookRequestStatus;
    
    public static BookRequestFixture withUser(User user) {
        return BookRequestFixture.builder().user(user).build();
    }
    
    public static BookRequestFixture withUserAndStatus(User user, BookRequestStatus bookRequestStatus) {
        return BookRequestFixture.builder().user(user).bookRequestStatus(bookRequestStatus).build();
    }
    
    public static BookRequestFixture withUserAndStatusAndParkingLot(User user, BookRequestStatus bookRequestStatus, ParkingLot parkingLot) {
        return BookRequestFixture.builder().user(user).bookRequestStatus(bookRequestStatus).parkingLot(parkingLot).build();
    }
    
    public BookRequest get() {
        return new BookRequest(parkingLot, user, failedAttempts, bookRequestStatus);
    }
}
