package com.utm.stanislav.parkingapp.web.controller;

import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import com.utm.stanislav.parkingapp.web.dto.ResponseMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.security.Principal;

@Controller
public class TestController {
    
    private SimpMessagingTemplate brokerMessagingTemplate;
    private FunctionMessageService functionMessageService;
    
    @Inject
    public void setFunctionMessageService(FunctionMessageService functionMessageService) {
        this.functionMessageService = functionMessageService;
    }
    
    @Inject
    public void setBrokerMessagingTemplate(SimpMessagingTemplate brokerMessagingTemplate) {
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }
    
    @MessageMapping("/test")
    public void testMessage(ResponseMessageDTO message, Principal principal) {
        System.out.println("I received a new message: " + message.toString());
    }
    
    @MessageMapping("/test2")
    public void testMessage2(String body) {
        System.out.println(body);
    }
    
//    @MessageMapping("/test")
//    @SendToUser(value = "/messages/function", broadcast = false)
//    public List<FunctionMessage> testMessages(Principal principal) {
//        List<FunctionMessage> functionMessages = new ArrayList<>();
//        FunctionMessage functionMessage = new FunctionMessage();
//        functionMessage.setId(UUID.randomUUID());
//        functionMessage.setMessageType(MessageType.FUNCTION_MESSAGE);
//        functionMessage.setFunctionCode(FunctionCode.STATUS_CHECK);
//        functionMessage.setAddress(2);
//        functionMessages.add(functionMessage);
//        return functionMessages;
//    }
}


