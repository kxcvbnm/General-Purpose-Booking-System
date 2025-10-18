package com.bookingsystem.booking.user.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.booking.shared.security.UserPrincipal;
import com.bookingsystem.booking.user.api.dtos.request.ChangePasswordRequest;
import com.bookingsystem.booking.user.api.dtos.request.UserUpdateRequest;
import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // @PostMapping
    // public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest req) {
    //     UserDTO body = userService.createUser(req);
    //     return ResponseEntity.status(201).body(body); // 201 Created
    // }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal UserPrincipal user, 
                                              @Valid @RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(userService.updateUser(user.getId(), req));
    }

    @PatchMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserPrincipal user,
                                               @Valid @RequestBody ChangePasswordRequest req) {
        userService.changePassword(user.getId(), req.currentPassword(), req.newPassword());                                        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
   
}
