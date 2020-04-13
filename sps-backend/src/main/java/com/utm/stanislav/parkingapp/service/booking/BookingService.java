package com.utm.stanislav.parkingapp.service.booking;

import com.utm.stanislav.parkingapp.dto.ParkingDTO;
import com.utm.stanislav.parkingapp.dto.ParkingLotDTO;
import com.utm.stanislav.parkingapp.exceptions.BookingException;

public interface BookingService {
    
    ParkingLotDTO book(ParkingDTO parkingDTO, String username) throws BookingException;
}
