package com.utm.stanislav.parkingapp.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utm.stanislav.parkingapp.enums.TokenType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    @JsonProperty("token")
    private String jwtToken;
    
    public static AuthenticationResponse from(String token) {
        return new AuthenticationResponse(token);
    }
}
