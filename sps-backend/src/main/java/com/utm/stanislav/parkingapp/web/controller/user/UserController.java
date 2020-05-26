package com.utm.stanislav.parkingapp.web.controller.user;

import com.utm.stanislav.parkingapp.web.dto.UserDTO;
import com.utm.stanislav.parkingapp.model.exceptions.UserNotFoundException;
import com.utm.stanislav.parkingapp.model.exceptions.UserValidationException;
import com.utm.stanislav.parkingapp.model.User;
import com.utm.stanislav.parkingapp.security.UserPrincipal;
import com.utm.stanislav.parkingapp.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final ModelMapper modelMapper;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                                      .stream()
                                      .map(this::convertToDTO)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws UserValidationException {
        User user = convertToEntity(userDTO);
        this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateUsername(@RequestParam(name = "username") String newUsername,
                                         Authentication authentication)
            throws UserValidationException, UserNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        this.userService.updateUsername(newUsername, userPrincipal.getUsername());
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Delete user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> deleteUser(@RequestParam(name = "username") String username) {
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
