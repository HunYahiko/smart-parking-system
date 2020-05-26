package com.utm.stanislav.parkingapp.web.controller.websocket;

import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import com.utm.stanislav.parkingapp.web.dto.ResponseMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ResponseMessageController {
    
    private final FunctionMessageService functionMessageService;
    
    @MessageMapping("/status")
    public void handleResponseMessage(ResponseMessageDTO responseMessageDTO) {
        functionMessageService.handleResponse(responseMessageDTO);
    }
}
