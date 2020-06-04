package com.utm.stanislav.parkingapp.service.functionmessage;

import com.utm.stanislav.parkingapp.model.BookRequest;
import com.utm.stanislav.parkingapp.model.enums.*;
import com.utm.stanislav.parkingapp.model.FunctionMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.model.RPiBridge;
import com.utm.stanislav.parkingapp.service.booking.BookRequestService;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import com.utm.stanislav.parkingapp.web.dto.ResponseMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FunctionMessageServiceImpl implements FunctionMessageService {
    
    private final ParkingLotService parkingLotService;
    private BookRequestService bookRequestService;
    
    @Inject
    public void setBookRequestService(BookRequestService bookRequestService) {
        this.bookRequestService = bookRequestService;
    }
    
    @Override
    @Transactional
    public List<FunctionMessage> generateFor(RPiBridge rPiBridge, FunctionCode functionCode) {
        List<ParkingLot> parkingLots = this.parkingLotService.getAllPairedWith(rPiBridge);
        List<FunctionMessage> functionMessages = new ArrayList<>();
        
        //parkingLots = filterUnresponsiveLots(parkingLots);
        
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
    
    @Override
    @Transactional
    public void handleStatusCheckResponse(ResponseMessageDto responseMessage) {
        UUID parkingLotId = responseMessage.getParkingLotId();
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getById(parkingLotId);
        parkingLotOptional.ifPresent(parkingLot -> {
            System.out.println("parkingLot: " + parkingLot.getLogicalId() + ", response status: " + responseMessage.getResponseStatus().getName());
            if (responseMessage.getResponseStatus() == ResponseStatus.UNRESPONSIVE) {
                handleUnresponsiveParkingLotMessage(parkingLot);
            }
            else {
                handleSuccessfulResponseMessage(parkingLot, responseMessage);
            }
        });
    }
    
    private void handleUnresponsiveParkingLotMessage(ParkingLot parkingLot) {
        if (parkingLot.getFailedResponseCount() > 5) {
            parkingLot.setParkingStatus(ParkingStatus.UNRESPONSIVE);
            return;
        }
        Integer failedResponseCount = parkingLot.getFailedResponseCount() + 1;
        parkingLot.setFailedResponseCount(failedResponseCount);
    }
    
    private void handleSuccessfulResponseMessage(ParkingLot parkingLot, ResponseMessageDto responseMessage) {
        resetFailedResponseCount(parkingLot);
        List<String> splitResponseData = Arrays.asList(responseMessage.getResponseData().split(","));
        Optional<ParkingStatus> newParkingStatus = ParkingStatus.valueOfByCode(Integer.parseInt(splitResponseData.get(1)));
        newParkingStatus.ifPresent(parkingLot::setParkingStatus);
    }
    
    private void resetFailedResponseCount(ParkingLot parkingLot) {
        parkingLot.setFailedResponseCount(0);
    }
    
    
    @Override
    @Transactional
    public void handleBlockingLotResponse(ResponseMessageDto responseMessage) {
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getById(responseMessage.getParkingLotId());
        List<String> splitResponseData = Arrays.asList(responseMessage.getResponseData().split(","));
        String bookingStatus = splitResponseData.get(1);
        if (responseMessage.getResponseStatus().equals(ResponseStatus.OK) && bookingStatus.equals("OK")) {
            if (parkingLotOptional.isPresent()) {
                Optional<BookRequest> bookRequestOptional = bookRequestService.getFor(parkingLotOptional.get());
                bookRequestOptional.ifPresent(bookRequest -> bookRequest.setBookRequestStatus(BookRequestStatus.CONFIRMED));
            }
        }
        else {
            if (parkingLotOptional.isPresent()) {
                Optional<BookRequest> bookRequestOptional = bookRequestService.getFor(parkingLotOptional.get());
                bookRequestOptional.ifPresent(bookRequest -> {
                    if (bookRequest.getFailedAttempts() > 3) {
                        bookRequest.setBookRequestStatus(BookRequestStatus.FAILED);
                    }
                    else {
                        bookRequest.setFailedAttempts(bookRequest.getFailedAttempts() + 1);
                    }
                });
            }
        }
    }
    
    @Override
    @Transactional
    public void handleUnblockLotResponse(ResponseMessageDto responseMessage) {
        Optional<ParkingLot> parkingLotOptional = parkingLotService.getById(responseMessage.getParkingLotId());
        List<String> splitResponseData = Arrays.asList(responseMessage.getResponseData().split(","));
        String unblockingStatus = splitResponseData.get(1);
        if (responseMessage.getResponseStatus().equals(ResponseStatus.OK) && unblockingStatus.equals("OK")) {
            if (parkingLotOptional.isPresent()) {
                Optional<BookRequest> bookRequestOptional = bookRequestService.getFor(parkingLotOptional.get());
                bookRequestOptional.ifPresent(bookRequest -> bookRequestService.delete(bookRequest));
            }
        }
        else {
            if (parkingLotOptional.isPresent()) {
                /* TODO
                    THIS SHOULD NOT HAPPEN, AS THIS MESSAGE WILL BE PROCESSED ONLY IF PARKING LOT WAS PREVIOUSLY BLOCKED
                 */
            }
        }
    }
}
