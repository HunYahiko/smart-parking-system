package com.utm.stanislav.parkingapp.service.auth;

import com.utm.stanislav.parkingapp.fixture.UserFixture;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import com.utm.stanislav.parkingapp.model.exceptions.InvalidCredentialsException;
import com.utm.stanislav.parkingapp.model.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.security.jwt.JwtProvider;
import com.utm.stanislav.parkingapp.service.user.UserService;
import com.utm.stanislav.parkingapp.web.controller.response.AuthenticationResponse;
import com.utm.stanislav.parkingapp.web.dto.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    
    @InjectMocks
    AuthenticationServiceImpl authenticationService;
    
    private String username;
    private LoginDto loginDto;
    private User user;
    
    
    @BeforeEach
    void setUp() {
        loginDto = new LoginDto();
        loginDto.setUsername("mock");
        loginDto.setPassword("mock");
        
        username = "mock";
        
        user = new User();
        
        
    }
    
    @Test
    void authenticate_withInvalidUsername_throwsInvalidCredentialsException() {
        userService = new UserServiceFake();
        authenticationService = new AuthenticationServiceImpl(userService, authenticationManager, jwtProvider, passwordEncoder);
        
        assertThatExceptionOfType(InvalidCredentialsException.class)
                .isThrownBy(() -> authenticationService.authenticate(loginDto));
    }
    
    @Test
    void authenticate_withValidUsernameAndInvalidPassword_throwsInvalidCredentialsException() {
        user.setUsername("mock");
        user.setPassword("mock");
    
        UserServiceMock userServiceMock = new UserServiceMock();
        authenticationService = new AuthenticationServiceImpl(userServiceMock, authenticationManager, jwtProvider, passwordEncoder);
        
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(Boolean.FALSE);
        
        assertThatExceptionOfType(InvalidCredentialsException.class)
                .isThrownBy(() -> authenticationService.authenticate(loginDto));
        
        userServiceMock.assertIsSatisfied();
    }
    
    @Test
    void authenticate_withValidUsernameAndValidPassword_returnsJwtToken() throws InvalidCredentialsException {
        String jwtToken = "random_token";
        user.setUsername("mock");
        user.setPassword("mock");
    
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(Boolean.TRUE);
        when(userService.getByUsername(username))
                .thenReturn(Optional.of(user));
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        doReturn(usernamePasswordAuthenticationToken)
                                                .when(authenticationManager).authenticate(usernamePasswordAuthenticationToken);
        
        when(jwtProvider.generateToken(usernamePasswordAuthenticationToken)).thenReturn(jwtToken);
        
        assertThat(authenticationService.authenticate(loginDto)).isEqualTo(AuthenticationResponse.from(jwtToken));
        
        verify(jwtProvider).generateToken(usernamePasswordAuthenticationToken);
        
    }
    
    class UserServiceFake implements UserService {
    
        @Override
        public Optional<User> getByUsername(String username) {
            return Optional.empty();
        }
    
        @Override
        public List<User> getAll() {
            return null;
        }
    
        @Override
        public void createOne(User user) throws UserValidationException {
        
        }
    
        @Override
        public void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException {
        
        }
    
        @Override
        public void deleteOne(String username) {
        
        }
    }
    
    class UserServiceMock implements UserService {
        
        List<User> mockedRepository;
        private int numberOfInvocations;
        
        UserServiceMock() {
            mockedRepository = new ArrayList<>();
            mockedRepository.add(UserFixture.withUsernameAndPassword("mock", "mock").get());
        }
    
        @Override
        public Optional<User> getByUsername(String username) {
            ++numberOfInvocations;
            return Optional.of(mockedRepository.get(0));
        }
    
        @Override
        public List<User> getAll() {
            return null;
        }
    
        @Override
        public void createOne(User user) throws UserValidationException {
        
        }
    
        @Override
        public void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException {
        
        }
    
        @Override
        public void deleteOne(String username) {
        
        }
        
        void assertIsSatisfied() {
            assertThat(numberOfInvocations).isEqualTo(1);
        }
    }
    
    class UserServiceDummy implements UserService {
        @Override
        public Optional<User> getByUsername(String username) {
            return Optional.empty();
        }
    
        @Override
        public List<User> getAll() {
            return null;
        }
    
        @Override
        public void createOne(User user) throws UserValidationException {
        
        }
    
        @Override
        public void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException {
        
        }
    
        @Override
        public void deleteOne(String username) {
        
        }
    }
    
    class UserServiceSaboteurStub implements UserService {
        @Override
        public Optional<User> getByUsername(String username) {
            return Optional.empty();
        }
    
        @Override
        public List<User> getAll() {
            return null;
        }
    
        @Override
        public void createOne(User user) throws UserValidationException {
            throw new UserValidationException("dummyMessage", ValidationInputField.USERNAME);
        }
    
        @Override
        public void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException {
        
        }
    
        @Override
        public void deleteOne(String username) {
        
        }
    }
    
    class UserServiceSpy implements UserService {
        
        private int numberOfInvocations;
        
        @Override
        public Optional<User> getByUsername(String username) {
            return Optional.empty();
        }
    
        @Override
        public List<User> getAll() {
            return null;
        }
    
        @Override
        public void createOne(User user) throws UserValidationException {
            ++numberOfInvocations;
        }
    
        @Override
        public void updateUsername(String newUsername, String username) throws UserValidationException, UserNotFoundException {
        
        }
    
        @Override
        public void deleteOne(String username) {
        
        }
        
        public int getNumberOfInvocations() {
            return numberOfInvocations;
        }
    }
}
