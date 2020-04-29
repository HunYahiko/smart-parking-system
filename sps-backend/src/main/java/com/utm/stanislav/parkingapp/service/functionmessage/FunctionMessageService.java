package com.utm.stanislav.parkingapp.service.functionmessage;

import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.FunctionMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;

import java.util.List;

public interface FunctionMessageService {
    
    List<FunctionMessage> generateFor(RPiBridge rPiBridge, FunctionCode functionCode);
    FunctionMessage generateFor(ParkingLot parkingLot, FunctionCode functionCode);
}
