package com.bookingsystem.booking.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookingsystem.booking.dto.requestdto.user.UserCreateRequest;
import com.bookingsystem.booking.dto.requestdto.user.UserUpdateRequest;
import com.bookingsystem.booking.dto.returndto.UserDTO;
import com.bookingsystem.booking.mappers.UserMapper;
import com.bookingsystem.booking.models.User;
import com.bookingsystem.booking.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest req) {
        User createdUser = userService.createUser(req);
        UserDTO body = UserMapper.toDto(createdUser);
        URI location = ServletUriComponentsBuilder
                       .fromCurrentRequest().path("/{id}")
                       .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(body); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(UserMapper.toDtoList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, 
                                              @Valid @RequestBody UserUpdateRequest req) {
        User updated = userService.updateUser(id, req);
        return ResponseEntity.ok(UserMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
   
}
