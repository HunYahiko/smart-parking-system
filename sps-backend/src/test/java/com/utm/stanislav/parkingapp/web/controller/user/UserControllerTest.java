package com.utm.stanislav.parkingapp.web.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utm.stanislav.parkingapp.configuration.security.SecurityConfiguration;
import com.utm.stanislav.parkingapp.fixture.UserDtoFixture;
import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.mapper.UserDtoToEntityMapper;
import com.utm.stanislav.parkingapp.model.mapper.UserToDtoMapper;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.security.jwt.JwtProvider;
import com.utm.stanislav.parkingapp.service.user.UserService;
import com.utm.stanislav.parkingapp.service.user.UserServiceImpl;
import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDto;
import com.utm.stanislav.parkingapp.web.dto.SignUpExceptionDto;
import com.utm.stanislav.parkingapp.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
class UserControllerTest {
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserDtoToEntityMapper dtoToEntityMapper;
    @MockBean
    private UserToDtoMapper userToDtoMapper;
    
    @InjectMocks
    private UserController userController;
    
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                          .apply(springSecurity())
//                          .standaloneSetup(userController)
//                          .setControllerAdvice(new UserControllerAdvice())
                          .build();
    }
    
    @Test
    void getAllUsers_whenInvokedWithNoAuthorization_returns401() throws Exception {
        mockMvc.perform(get("/v1/api/users")).andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(roles = {"MOCK"})
    void getAllUsers_whenInvokedWithRoleDifferentThanUserOrAdmin_returns403() throws Exception {
        mockMvc.perform(get("/v1/api/users")).andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser()
    void getAllUsers_whenInvoked_returnsAllUsers() throws Exception {
        List<User> userListMock = Collections.singletonList(UserFixture.usernameOnly("mock").get());
        List<UserDto> userDtoListMock = Collections.singletonList(UserDtoFixture.usernameOnly("mock").get());
        when(userToDtoMapper.mapList(userListMock)).thenReturn(userDtoListMock);
        when(userService.getAll()).thenReturn(userListMock);
        mockMvc.perform(get("/v1/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("mock"));
    }
    
    @Test
    void createUser_whenInvokedWithTakenUsername_returnsSingUpExceptionDtoWithUsernameField() throws Exception {
        User user = UserFixture.usernameOnly("mock").get();
        UserDto userDto = UserDtoFixture.usernameOnly("mock").get();
        when(dtoToEntityMapper.map(userDto)).thenReturn(user);
        doThrow(new UserValidationException("message", ValidationInputField.USERNAME)).when(userService).createOne(user);
        
        mockMvc.perform(post("/v1/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.inputField").value(ValidationInputField.USERNAME.getName()));
    
        verify(userService).createOne(user);
        
    }
    
    @Test
    void createUser_whenInvokedWithTakenPhoneNumber_returnsSignUpExceptionDtoWithPhoneNumberField() throws Exception {
        User user = UserFixture.usernameOnly("mock").get();
        UserDto userDto = UserDtoFixture.usernameOnly("mock").get();
        when(dtoToEntityMapper.map(userDto)).thenReturn(user);
        doThrow(new UserValidationException("message", ValidationInputField.PHONE_NUMBER)).when(userService).createOne(user);
    
        mockMvc.perform(post("/v1/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.inputField").value(ValidationInputField.PHONE_NUMBER.getName()));
        
        verify(userService).createOne(user);
    }
    
    @Test
    void createUser_whenInvoked_returns201() throws Exception {
        User user = UserFixture.usernameOnly("mock").get();
        UserDto userDto = UserDtoFixture.usernameOnly("mock").get();
        when(dtoToEntityMapper.map(userDto)).thenReturn(user);
    
        mockMvc.perform(post("/v1/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }
    
//    @Test
//    @WithMockUser()
//    @WithUserDetails
//    void updateUsername_whenInvokedWithTakenUsername_returnsSingUpExceptionDtoWithUsernameField() throws Exception {
//        User user = UserFixture.usernameOnly("mock").get();
//        String newUsername = "newMock";
//
//        doThrow(new UserValidationException("message", ValidationInputField.USERNAME)).when(userService).updateUsername(newUsername, user.getUsername());
//
//        mockMvc.perform(put("/v1/api/users").param("username", newUsername).principal(UserPrincipal.create(user)))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.inputField").value(ValidationInputField.USERNAME.getName()));
//    }
}
