package com.utm.stanislav.parkingapp.web.interceptor.decorator;

import com.utm.stanislav.parkingapp.model.exceptions.HeaderValueNotFoundException;
import javafx.util.Pair;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHeadersDecorator {
    private static final String BRIDGE_HEADER_NAME = "bridge_id";
    private static final String LOGIN_HEADER_NAME = "login";
    private static final String PASSWORD_HEADER_NAME = "password";
    
    private MessageHeaders messageHeaders;
    
    public MessageHeadersDecorator(MessageHeaders messageHeaders) {
        this.messageHeaders = messageHeaders;
    }
    
    public String getBridgeIdHeaderValue() throws HeaderValueNotFoundException {
        Object nativeHeaders = getNativeHeadersOrReturnEmptyMap();
        return retrieveHeaderValue(nativeHeaders, BRIDGE_HEADER_NAME);
    }
    
    public Pair<String, String> getLoginPasswordHeaders() throws HeaderValueNotFoundException {
        Object nativeHeaders = getNativeHeadersOrReturnEmptyMap();
        String login = retrieveHeaderValue(nativeHeaders, LOGIN_HEADER_NAME);
        String password = retrieveHeaderValue(nativeHeaders, PASSWORD_HEADER_NAME);
        return new Pair<>(login, password);
    }
    
    private Object getNativeHeadersOrReturnEmptyMap() {
        Object nativeHeaders = this.messageHeaders.get(StompHeaderAccessor.NATIVE_HEADERS);
        if (nativeHeaders == null) {
            return new HashMap<String, List<String>>();
        } else {
            return nativeHeaders;
        }
    }
    
    private String retrieveHeaderValue(Object nativeHeaders, String headerName) throws HeaderValueNotFoundException {
        if (nativeHeaders instanceof Map) {
            Map<String, List<String>> headerMap = (Map<String, List<String>>) nativeHeaders;
            List<String> headerValues = headerMap.get(headerName);
            if (headerValues == null) {
                throw new HeaderValueNotFoundException("Header " + headerName + " does not exist in headers!");
            }
            return headerValues.get(0);
        }
        throw new HeaderValueNotFoundException("Could not retrieve headers as object is not instanceOf Map!");
    }
    
}
