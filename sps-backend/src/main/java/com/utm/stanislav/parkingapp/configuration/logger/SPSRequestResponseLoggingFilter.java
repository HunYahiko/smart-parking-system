package com.utm.stanislav.parkingapp.configuration.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Named
@Slf4j
public class SPSRequestResponseLoggingFilter extends OncePerRequestFilter {
    
    private static final String DEFAULT_REQUEST_MESSAGE_PREFIX = "REQUEST ===>";
    private static final String DEFAULT_RESPONSE_MESSAGE_PREFIX = "RESPONSE ===>";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        HttpServletResponse responseToUse = response;
        
        if (isFirstRequest) {
            requestToUse = wrap(request);
            responseToUse = wrap(response);
            beforeRequest(requestToUse);
        }
        try {
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            if (!isAsyncStarted(requestToUse)) {
                afterRequest(responseToUse);
                ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper)responseToUse;
                responseWrapper.copyBodyToResponse();
            }
        }
    }
 
    private HttpServletRequest wrap(HttpServletRequest request) {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            return new ContentCachingRequestWrapper(request);
        }
        return request;
    }
    
    private HttpServletResponse wrap(HttpServletResponse response) {
        if (!(response instanceof ContentCachingResponseWrapper)) {
            return new ContentCachingResponseWrapper(response);
        }
        return response;
    }
    
    private void beforeRequest(HttpServletRequest request) {
        logRequest(request);
    }
    
    private void afterRequest(HttpServletResponse response) {
        logResponse(response);
    }
    
    private void logRequest(HttpServletRequest request) {
        String suffix = "";
        String logMessage = createLogMessage(request, DEFAULT_REQUEST_MESSAGE_PREFIX, suffix);
        log.info(logMessage);
    }
    
    private void logResponse(HttpServletResponse response) {
        String suffix = "";
        String logMessage = createLogMessage(response, DEFAULT_RESPONSE_MESSAGE_PREFIX, suffix);
        log.info(logMessage);
    }
    
    private String createLogMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append(request.getMethod()).append(" ");
        msg.append(request.getRequestURI());
        
        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }
        
        String client = request.getRemoteAddr();
        if (StringUtils.hasLength(client)) {
            msg.append(", client=").append(client);
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            msg.append(", session=").append(session.getId());
        }
        String user = request.getRemoteUser();
        if (user != null) {
            msg.append(", user=").append(user);
        }
        
        HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
        msg.append(", headers=").append(headers);
    
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        String payload = getMessagePayload(wrapper.getContentAsByteArray(), request.getCharacterEncoding());
        msg.append(", payload=").append(payload);
    
        msg.append(suffix);
        return msg.toString();
    }
    
    private String createLogMessage(HttpServletResponse response, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append(response.getStatus()).append(" ");
        
        HttpHeaders headers = new ServletServerHttpResponse(response).getHeaders();
        msg.append(", headers=").append(headers);
        
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        String payload = getMessagePayload(wrapper.getContentAsByteArray(), response.getCharacterEncoding());
        msg.append(", payload=").append(payload);
        
        msg.append(suffix);
        return msg.toString();
    }
    
    private String getMessagePayload(byte[] buffer, String characterEncoding) {
        try {
            return new String(buffer, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            return "[unknown]";
        }
    }
    
}
