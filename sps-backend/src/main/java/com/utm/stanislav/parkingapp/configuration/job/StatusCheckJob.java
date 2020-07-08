package com.utm.stanislav.parkingapp.configuration.job;

import com.utm.stanislav.parkingapp.model.enums.FunctionCode;
import com.utm.stanislav.parkingapp.model.*;
import com.utm.stanislav.parkingapp.repository.RPiBridgeRepository;
import com.utm.stanislav.parkingapp.service.functionmessage.FunctionMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class StatusCheckJob {
    
    private final RPiBridgeRepository repository;
    private final FunctionMessageService functionMessageService;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    
    
    @Scheduled(fixedDelay = 4000, initialDelay = 4000)
    @Transactional
    public void scheduleStatusCheckJob() {
        StopWatch stopWatch = new StopWatch("StatusCheckJobExecutionTime");
        stopWatch.start();
        sendStatusCheckMessages();
        stopWatch.stop();
        System.out.println("StatusCheckJob Execution Time: " + stopWatch.getTotalTimeSeconds());
    }
    
//    @Scheduled(fixedDelay = 10000, initialDelay = 4000)
//    @Transactional
//    public void testSchedule() {
//        User user = this.userRepository.findByUsername("HohohoHoroso").get();
//        this.bookingService.book(this.parkingRepository.findByLogicalId("DECEBAL_PARKING").get(), user);
//    }
    
    @Transactional
    public void sendStatusCheckMessages() {
        List<RPiBridge> rPiBridges = this.repository.findAll();
        for (RPiBridge bridge : rPiBridges) {
            if (bridge.getIsConnected()) {
                //System.out.print("Processing bridge " + bridge.getLogicalId() + " with ");
                List<FunctionMessage> functionMessages = functionMessageService.generateFor(
                        bridge, FunctionCode.STATUS_CHECK);
    
//                System.out.println(functionMessages.size() + " parking lots");
//                System.out.println("Bridge session " + bridge.getSessionId());

                this.brokerMessagingTemplate.convertAndSendToUser(bridge.getSessionId(),
                        "/messages/function",
                        functionMessages,
                        createHeaders(bridge.getSessionId()));
            }
            else {
                System.out.println("Bridge[" + bridge.getLogicalId() + "] is not active!");
            }
        }
    }
    
    private MessageHeaders createHeaders(String sessionId) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        stompHeaderAccessor.setSessionId(sessionId);
        stompHeaderAccessor.setLeaveMutable(Boolean.TRUE);
        return stompHeaderAccessor.getMessageHeaders();
    }
}
