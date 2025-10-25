package com.bookingsystem.booking.room.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bookingsystem.booking.room.api.dtos.request.RoomCreateRequest;
import com.bookingsystem.booking.room.api.dtos.response.RoomDTO;
import com.bookingsystem.booking.room.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(
        description = "Create a new room (ADMIN)",
        summary = "Create a new room (ADMIN)",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Created Success"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorized"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Invalid token"
            )
        }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomCreateRequest req) {
        RoomDTO body = roomService.createRoom(req);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()     
            .path("/{id}")
            .buildAndExpand(body.id())  
            .toUri();

        return ResponseEntity.created(location).body(body); // 201 Created
    }

    @Operation(
        description = "Get all rooms (Authenticated)",
        summary = "Get all rooms (Authenticated)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorized"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Invalid token"
            )
        }
    )
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @Operation(
        description = "Get room by id (ADMIN)",
        summary = "Get room by id (ADMIN)",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Success"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorized"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Invalid token"
            )
        }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @Operation(
        description = "delete a room (ADMIN)",
        summary = "delete a room (ADMIN)",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Success no content"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Unauthorized"
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Invalid token"
            )
        }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
