package com.utm.stanislav.parkingapp.service.functionmessage;

import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.enums.MessageType;
import com.utm.stanislav.parkingapp.model.enums.ParkingStatus;
import com.utm.stanislav.parkingapp.model.FunctionMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FunctionMessageServiceImpl implements FunctionMessageService {
    
    private final ParkingLotService parkingLotService;
    
    @Override
    @Transactional
    public List<FunctionMessage> generateFor(RPiBridge rPiBridge, FunctionCode functionCode) {
        List<ParkingLot> parkingLots = this.parkingLotService.getAllLotsPairedWith(rPiBridge);
        List<FunctionMessage> functionMessages = new ArrayList<>();
        
        parkingLots = filterUnresponsiveLots(parkingLots);
        
        for (ParkingLot parkingLot : parkingLots) {
            FunctionMessage functionMessage = generateFor(parkingLot, functionCode);
            functionMessages.add(functionMessage);
        }
        return functionMessages;
    }
    
    @Override
    @Transactional
    public FunctionMessage generateFor(ParkingLot parkingLot, FunctionCode functionCode) {
        FunctionMessage functionMessage = new FunctionMessage();
        functionMessage.setFunctionCode(functionCode);
        functionMessage.setMessageType(MessageType.FUNCTION_MESSAGE);
        functionMessage.setAddress(parkingLot.getAddress());
        functionMessage.setParkingLotId(parkingLot.getId());
        
        return functionMessage;
    }
    
    private List<ParkingLot> filterUnresponsiveLots(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                       .filter((parkingLot -> !parkingLot.getParkingStatus().equals(ParkingStatus.UNRESPONSIVE)))
                       .collect(Collectors.toList());
    }
}
