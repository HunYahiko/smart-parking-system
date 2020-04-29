package com.utm.stanislav.parkingapp.web.interceptor.decorator;

import com.utm.stanislav.parkingapp.model.exceptions.HeaderValueNotFoundException;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHeadersDecorator {
    private static final String BRIDGE_HEADER_NAME = "bridge_id";
    
    private MessageHeaders messageHeaders;
    
    public MessageHeadersDecorator(MessageHeaders messageHeaders) {
        this.messageHeaders = messageHeaders;
    }
    
    public String getBridgeIdHeaderValue() throws HeaderValueNotFoundException {
        Object nativeHeaders = getNativeHeadersOrReturnEmptyMap();
        return retrieveBridgeId(nativeHeaders);
    }
    
    private Object getNativeHeadersOrReturnEmptyMap() {
        Object nativeHeaders = this.messageHeaders.get(StompHeaderAccessor.NATIVE_HEADERS);
        if (nativeHeaders == null) {
            return new HashMap<String, List<String>>();
        } else {
            return nativeHeaders;
        }
    }
    
    private String retrieveBridgeId(Object nativeHeaders) throws HeaderValueNotFoundException {
        if(nativeHeaders instanceof Map) {
            Map<String, List<String>> headerMap = (Map<String, List<String>>) nativeHeaders;
            List<String> headerValues = headerMap.get(BRIDGE_HEADER_NAME);
            if (headerValues == null) {
                throw new HeaderValueNotFoundException("Could not retrieve headers values since values list is empty!");
            } else {
                return headerValues.get(0);
            }
        } else {
            throw new HeaderValueNotFoundException("Could not retrieve headers as object is not instanceof Map!");
        }
    }
}
