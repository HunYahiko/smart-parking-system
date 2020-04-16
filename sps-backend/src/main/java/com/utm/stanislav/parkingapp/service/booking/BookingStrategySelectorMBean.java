package com.utm.stanislav.parkingapp.service.booking;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import javax.inject.Named;

@ManagedResource(
        objectName = "PD:category=MBeans, name=bookingStrategySelectorBean",
        description = "Booking Strategy Selector Bean"
)
@Named(value = "bookingStrategySelector")
public class BookingStrategySelectorMBean {
    
    private final String defaultBookingStrategy = "plainBookingStrategy";
    private String bookingStrategy;
    
    public BookingStrategySelectorMBean() {
        this.bookingStrategy = defaultBookingStrategy;
    }
    
    @ManagedOperation(description = "Changed booking strategy")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "strategy", description = "new strategy name")
    })
    public void setBookingStrategy(String strategy) {
        this.bookingStrategy = strategy;
    }
    
    public String getBookingStrategy() {
        return bookingStrategy;
    }
    
    public String getDefaultBookingStrategy() {
        return defaultBookingStrategy;
    }
}
