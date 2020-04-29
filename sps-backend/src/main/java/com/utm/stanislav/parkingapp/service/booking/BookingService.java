package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.web.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.web.dto.ParkingLotDTO;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;

public interface BookingService {
    
    ParkingLotDTO book(ParkingDTO parkingDTO, String username) throws BookingException;
}
