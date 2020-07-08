package com.utm.stanislav.parkingapp.web.controller.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.exceptions.BookingException;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.booking.BookRequestService;
import com.utm.stanislav.parkingapp.service.user.UserService;
import com.utm.stanislav.parkingapp.web.dto.ParkingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
class BookingControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private BookRequestService bookRequestService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                          .apply(springSecurity())
                          .build();
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void bookSpotIn_whenInvokedWithUserThatDoesNotExists_returns400() throws Exception{
        when(userService.getByUsername("user")).thenReturn(Optional.empty());
        ParkingDto parkingDto = new ParkingDto(UUID.randomUUID(), "mock");
        mockMvc.perform(post("/v1/api/booking/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(parkingDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void bookSpotIn_whenInvokedWithUserThatExistsButFailureOnCreatingBookRequest_returns400() throws Exception {
        User user = UserFixture.usernameOnly("user").get();
        when(userService.getByUsername("user")).thenReturn(Optional.of(user));
        ParkingDto parkingDto = new ParkingDto(UUID.randomUUID(), "mock");
        doThrow(BookingException.class).when(bookRequestService).createOne(parkingDto, user);
        mockMvc.perform(post("/v1/api/booking/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(parkingDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void bookSpotIn_whenInvoked_return201() throws Exception {
        User user = UserFixture.usernameOnly("user").get();
        when(userService.getByUsername("user")).thenReturn(Optional.of(user));
        ParkingDto parkingDto = new ParkingDto(UUID.randomUUID(), "mock");
        mockMvc.perform(post("/v1/api/booking/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(parkingDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void confirmArrival_whenInvokedWithUserThatDoesNotExists_return400() throws Exception {
        when(userService.getByUsername("user")).thenReturn(Optional.empty());
        mockMvc.perform(post("/v1/api/booking/confirm/{id}", UUID.randomUUID()))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void confirmArrival_whenInvokedWithUserThatDoesExistButNoBookRequestWithId_return400() throws Exception {
        User user = UserFixture.usernameOnly("user").get();
        when(userService.getByUsername("user")).thenReturn(Optional.of(user));
        UUID uuid = UUID.randomUUID();
        doThrow(BookingException.class).when(bookRequestService).confirmArrival(uuid, user);
        mockMvc.perform(post("/v1/api/booking/confirm/{id}", uuid))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockCustomUserPrincipal
    void confirmArrival_whenInvoked_returns200() throws Exception {
        User user = UserFixture.usernameOnly("user").get();
        when(userService.getByUsername("user")).thenReturn(Optional.of(user));
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(post("/v1/api/booking/confirm/{id}", uuid))
                .andExpect(status().isOk());
    }
    
}
