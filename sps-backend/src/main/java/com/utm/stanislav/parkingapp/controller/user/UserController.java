package com.utm.stanislav.parkingapp.controller.user;

import com.utm.stanislav.parkingapp.dto.UserDTO;
import com.utm.stanislav.parkingapp.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.repository.UserRepository;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.user.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/users")
@AllArgsConstructor
public class UserController {
    
    private UserService userService;
    private ModelMapper modelMapper;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                                      .stream()
                                      .map(this::convertToDTO)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) throws UserValidationException {
        User user = convertToEntity(userDTO);
        this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateUsername(@RequestParam(name = "username") String newUsername,
                                         Authentication authentication)
            throws UserValidationException, UserNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        this.userService.updateUsername(newUsername, userPrincipal.getUsername());
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity deleteUser(@RequestParam(name = "username") String username) {
        /* TODO
            1. Check authority.
            2. If user tries to delete another user, restrict.
            3. If admin tries to delete another user, allow.
            4. If user tries to delete himself, allow.
         */
        this.userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }
    
    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
    
    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
    
}
