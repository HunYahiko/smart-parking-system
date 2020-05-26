package com.utm.stanislav.parkingapp.configuration.job;

import com.utm.stanislav.parkingapp.model.LayoutUpdateMessage;
import com.utm.stanislav.parkingapp.model.ParkingLot;
import com.utm.stanislav.parkingapp.service.layout.LayoutUpdateService;
import com.utm.stanislav.parkingapp.service.parkinglot.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class LayoutUpdateJob {
    
    private final ParkingLotService parkingLotService;
    private final LayoutUpdateService layoutUpdateService;
    private final SimpMessagingTemplate brokerMessagingTemplate;
    
    @Scheduled(fixedDelay = 2000L, initialDelay = 1000L)
    @Transactional
    public void scheduleLayoutUpdateJob() {
        sendLayoutUpdateMessages();
    }
    
    @Transactional
    public void sendLayoutUpdateMessages() {
        List<ParkingLot> parkingLots = parkingLotService.getAll();
        List<LayoutUpdateMessage> layoutUpdateMessages = layoutUpdateService.generateFor(parkingLots);
        for (LayoutUpdateMessage updateMessage : layoutUpdateMessages) {
            this.brokerMessagingTemplate.convertAndSend("/layout", updateMessage);
        }
        
    }
}
