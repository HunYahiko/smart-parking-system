package com.utm.stanislav.parkingapp.service.user;

import com.utm.stanislav.parkingapp.model.Role;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.model.enums.ValidationInputField;
import com.utm.stanislav.parkingapp.model.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.service.role.RoleService;
import com.utm.stanislav.parkingapp.validators.user.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    
    @InjectMocks
    UserServiceImpl userService;
    
    private String username;
    private User user;
    private Role role;
    
    @BeforeEach
    void setUp() {
        username = "mockUsername";
        user = new User();
        user.setUsername(username);
        user.setEmail("mockEmail");
        user.setPhoneNumber("mockPhoneNumber");
        user.setFirstName("mockFirstName");
        user.setLastName("mockLastName");
        user.setPassword("mockPassword");
    
        role = new Role();
        role.setName("USER");
    }
    
    @Test
    void getByUsername_whenNoUserWithSuchUsername_returnsEmptyOptional() {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());
        
        assertThat(userService.getByUsername(username)).isEqualTo(Optional.empty());
    }
    
    @Test
    void getByUsername_whenExistsUserWithSuchName_returnsOptionalUser() {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        
        assertThat(userService.getByUsername(username)).isEqualTo(Optional.of(user));
    }
    
    @Test
    void getAll_whenNoUsersExists_returnsEmptyList() {
        when(userRepository.findAll())
                .thenReturn(Collections.emptyList());
        
        assertThat(userService.getAll().isEmpty()).isTrue();
    }
    
    @Test
    void getAll_whenOneUserExists_returnsListWithSizeOne() {
        List<User> users = new ArrayList<>();
        users.add(user);
        
        when(userRepository.findAll())
                .thenReturn(users);
        
        assertThat(userService.getAll().size()).isEqualTo(1);
    }
    
    @Test
    void createOne_whenUserIsNotValid_throwsUserValidationException() throws UserValidationException {
        doThrow(UserValidationException.class).when(userValidator).validateUser(user);
        
        assertThrows(UserValidationException.class, () -> userService.createOne(user));
    }
    
    @Test
    void createOne_whenUserIsValid_UserHasUSERRoleSet() throws UserValidationException {
        when(roleService.getBaseRole()).then((answer) -> role);
        userService.createOne(user);
        
        assertThat(user.getRoles().contains(role)).isTrue();
    }
    
    @Test
    void createOne_whenUserIsValid_SavesUserOnce() throws UserValidationException {
        userService.createOne(user);
        verify(userRepository).save(user);
    }
    
    @Test
    void updateUsername_whenNewUsernameExists_throwsUserValidationExceptionWithValidationInputFieldUSERNAME() {
        when(userRepository.existsByUsername("dummy")).thenReturn(Boolean.TRUE);
        
        UserValidationException exception = assertThrows(UserValidationException.class, () -> userService.updateUsername("dummy", username));
        
        assertThat(exception.getInputField()).isEqualTo(ValidationInputField.USERNAME);
    }
    
    @Test
    void updateUsername_whenUserDoesNotExist_throwsUserNotFoundException() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        assertThrows(UserNotFoundException.class, () -> userService.updateUsername("dummy", username));
    }
    
    @Test
    void updateUsername_whenUserDoesExist_setsNewUsernameOnUser() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        
        assertDoesNotThrow(() ->userService.updateUsername("dummy", username));
        
        assertThat(user.getUsername()).isEqualTo("dummy");
    }
    
    @Test
    void deleteOne_whenCalled_deletesUserOnce() {
        userService.deleteOne(username);
        verify(userRepository).deleteByUsername(username);
    }
}
