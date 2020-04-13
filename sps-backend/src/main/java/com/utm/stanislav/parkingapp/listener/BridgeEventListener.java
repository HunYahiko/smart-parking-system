package com.utm.stanislav.parkingapp.listener;

import com.utm.stanislav.parkingapp.exceptions.RPiBridgeNotFoundException;
import com.utm.stanislav.parkingapp.service.rpibridge.RPiBridgeService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BridgeEventListener implements ApplicationListener<SessionDisconnectEvent>{
    
    private RPiBridgeService rPiBridgeService;
    
    @Inject
    public void setrPiBridgeService(RPiBridgeService rPiBridgeService) {
        this.rPiBridgeService = rPiBridgeService;
    }
    
    @Override
    @Transactional
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        try {
            this.rPiBridgeService.removeSessionFromBridge(sessionId);
        } catch (RPiBridgeNotFoundException ex) {
            System.out.println("Could not find bridge by session!");
        }
    }
    
    @EventListener
    public void onApplicationEvent(SessionConnectedEvent event) {
        System.out.println("new event");
    }
}
