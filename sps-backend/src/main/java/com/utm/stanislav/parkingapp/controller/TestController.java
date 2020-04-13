package com.utm.stanislav.parkingapp.controller;

import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

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
    
//    @MessageMapping("/test")
//    @SendToUser(value = "/messages/command", broadcast = false)
//    public List<Message> testMessage(CommandMessage message, Principal principal) {
//        System.out.println("I received a new message: " + message);
//        //this.brokerMessagingTemplate.convertAndSend("/outgoing/test", "Backfire bitch");
//        List<Message> messageList = new ArrayList<>();
//        messageList.add(new CommandMessage(12, MessageType.COMMAND_MESSAGE, "doMe"));
//        messageList.add(new CommandMessage(13, MessageType.COMMAND_MESSAGE, "touchMe"));
//        return messageList;
//
//    }
    
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


