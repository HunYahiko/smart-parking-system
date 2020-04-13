package com.utm.stanislav.parkingapp.controller.response;

import com.utm.stanislav.parkingapp.enums.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    private String token;
    private TokenType type;
    
    public static AuthenticationResponse from(String token) {
        return new AuthenticationResponse(token, TokenType.BEARER);
    }
}
