package com.utm.stanislav.parkingapp.web.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("token")
    private String jwtToken;
    
    public static AuthenticationResponse from(String token) {
        return new AuthenticationResponse(token);
    }
}
