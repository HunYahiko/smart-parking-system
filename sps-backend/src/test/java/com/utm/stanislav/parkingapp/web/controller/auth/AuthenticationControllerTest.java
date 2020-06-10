package com.utm.stanislav.parkingapp.web.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;
import com.utm.stanislav.parkingapp.service.auth.AuthenticationService;
import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    
    @Mock
    private AuthenticationService authenticationService;
    
    @InjectMocks
    private AuthController authController;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                  .standaloneSetup(authController)
                  .setControllerAdvice(new AuthControllerAdvice())
                  .build();
    }
    
    @Test
    void authenticateUser_whenInvokedAndUserNotFound_returns404() throws Exception {
        LoginDto loginDto = new LoginDto("mock", "mock");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginDto);
        System.out.println(requestJson);
        lenient().when(authenticationService.authenticate(loginDto)).thenThrow(InvalidCredentialsException.class);
        mockMvc.perform(post("/v1/api/auth/sing-in").content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
    
    @Test
    void authenticateUser_whenInvokedAndUserFound_returnsTokenForUser() throws Exception {
        LoginDto loginDto = new LoginDto("mock", "mock");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginDto);
    
        String mockToken = "mockToken";
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(mockToken);
        
        lenient().when(authenticationService.authenticate(loginDto)).thenReturn(authenticationResponse);
        
        mockMvc.perform(post("/v1/api/auth/sing-in").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(authenticationResponse.getJwtToken()));
    }
}
