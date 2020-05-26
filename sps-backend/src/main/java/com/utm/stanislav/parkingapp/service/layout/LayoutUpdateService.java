package com.utm.stanislav.parkingapp.service.layout;


import com.utm.stanislav.parkingapp.model.LayoutUpdateMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;

import java.util.List;

public interface LayoutUpdateService {

    List<LayoutUpdateMessage> generateFor(List<ParkingLot> parkingLots);
    LayoutUpdateMessage generateOne(ParkingLot parkingLot);
}
