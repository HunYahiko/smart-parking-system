package com.utm.stanislav.parkingapp.web.controller.websocket;

import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import com.utm.stanislav.parkingapp.web.dto.ResponseMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ResponseMessageController {
    
    private final FunctionMessageService functionMessageService;
    
    @MessageMapping("/status")
    public void handleStatusCheckResponse(ResponseMessageDto responseMessageDto) {
        functionMessageService.handleStatusCheckResponse(responseMessageDto);
    }
    
    @MessageMapping("/blockLot")
    public void handleBlockLotResponse(ResponseMessageDto responseMessageDto) {
        functionMessageService.handleBlockingLotResponse(responseMessageDto);
    }
    
    @MessageMapping("/unblockLot")
    public void handleUnblockLotResponse(ResponseMessageDto responseMessageDto) {
        functionMessageService.handleUnblockLotResponse(responseMessageDto);
    }
}
