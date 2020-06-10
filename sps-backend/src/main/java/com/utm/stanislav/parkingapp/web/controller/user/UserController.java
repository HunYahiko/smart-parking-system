package com.utm.stanislav.parkingapp.web.controller.user;

import com.utm.stanislav.parkingapp.model.mapper.UserDtoToEntityMapper;
import com.utm.stanislav.parkingapp.model.mapper.UserToDtoMapper;
import com.utm.stanislav.parkingapp.web.dto.UserDto;
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

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserDtoToEntityMapper dtoToEntityMapper;
    private final UserToDtoMapper userToDtoMapper;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userToDtoMapper.mapList(userService.getAll());
        return ResponseEntity.ok(users);
    }
    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDTO) throws UserValidationException {
        User user = dtoToEntityMapper.map(userDTO);
        this.userService.createOne(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateUsername(@RequestParam(name = "username") String newUsername,
                                            Principal principal)
            throws UserValidationException, UserNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) principal;
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
        this.userService.deleteOne(username);
        return ResponseEntity.noContent().build();
    }
    
}
