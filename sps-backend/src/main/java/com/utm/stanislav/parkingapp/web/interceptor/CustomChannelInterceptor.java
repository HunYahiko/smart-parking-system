package com.utm.stanislav.parkingapp.web.interceptor;

import com.utm.stanislav.parkingapp.web.interceptor.decorator.MessageHeadersDecorator;
import com.utm.stanislav.parkingapp.model.exceptions.HeaderValueNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.service.rpibridge.RPiBridgeService;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class CustomChannelInterceptor implements ExecutorChannelInterceptor {
    
    private final RPiBridgeService rPiBridgeService;
    
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (isConnectCommand(headerAccessor.getCommand())) {
            MessageHeadersDecorator messageHeadersDecorator = new MessageHeadersDecorator(message.getHeaders());
            try {
                String bridgeId = messageHeadersDecorator.getBridgeIdHeaderValue();
                this.rPiBridgeService.setSessionIdOn(bridgeId, headerAccessor.getSessionId());
            } catch (HeaderValueNotFoundException | RPiBridgeNotFoundException ex) {
                try {
                    Pair<String, String> loginPasswordPair = messageHeadersDecorator.getLoginPasswordHeaders();
                    if (!(loginPasswordPair.getKey().equals("admin") && loginPasswordPair.getValue().equals("admin"))) {
                        throw new IllegalArgumentException("Wrong login and passcode!");
                    }
                } catch (HeaderValueNotFoundException exception) {
                    throw new IllegalArgumentException(exception.getMessage());
                }
            }
        }
        return message;
    }
    
    private boolean isConnectCommand(StompCommand command) {
        return StompCommand.CONNECT.equals(command);
    }
    
}
