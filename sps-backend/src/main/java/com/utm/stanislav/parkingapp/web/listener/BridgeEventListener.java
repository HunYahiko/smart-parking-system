package com.utm.stanislav.parkingapp.web.listener;

import com.utm.stanislav.parkingapp.model.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.service.rpibridge.RPiBridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class BridgeEventListener implements ApplicationListener<SessionDisconnectEvent>{
    
    private final RPiBridgeService rPiBridgeService;
    
    @Override
    @Transactional
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        try {
            this.rPiBridgeService.removeSessionIdFrom(sessionId);
        } catch (RPiBridgeNotFoundException ex) {
            System.out.println("Could not find bridge by session!");
        }
    }
    
    @EventListener
    public void onApplicationEvent(SessionConnectedEvent event) {
        System.out.println("new event");
    }
}
