package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.web.dto.BookRequestDto;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRequestService {
    
    void createOne(ParkingDto parkingDTO, User user) throws BookingException;
    Optional<BookRequest> getFor(ParkingLot parkingLot);
    void confirmArrival(UUID bookRequestId, User user) throws BookingException;
    void delete(BookRequest bookRequest);
    List<BookRequestDto> getByUser(User user);
}
