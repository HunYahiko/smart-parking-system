package com.utm.stanislav.parkingapp.service.layout;

import com.utm.stanislav.parkingapp.model.LayoutUpdateMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LayoutUpdateServiceImpl implements LayoutUpdateService {
    
    @Override
    public List<LayoutUpdateMessage> generateFor(List<ParkingLot> parkingLots) {
        List<LayoutUpdateMessage> layoutUpdateMessages = new ArrayList<>();
        for (ParkingLot parkingLot : parkingLots) {
            LayoutUpdateMessage updateMessage = generateOne(parkingLot);
            layoutUpdateMessages.add(updateMessage);
        }
        return layoutUpdateMessages;
    }
    
    @Override
    public LayoutUpdateMessage generateOne(ParkingLot parkingLot) {
        return new LayoutUpdateMessage(parkingLot.getLogicalId(),
                parkingLot.getLayoutObject().getId(),
                parkingLot.getParkingStatus());
    }
}
