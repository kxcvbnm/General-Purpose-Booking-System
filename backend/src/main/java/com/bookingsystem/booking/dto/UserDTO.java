package com.bookingsystem.booking.dto;

public class UserDTO {

    private final Long id;
    private final String username;
    private final String email;
    private final String role;

    public UserDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
      return this.id;
    }

    public String getUsername() {
      return this.username;
    }

    public String getEmail() {
      return this.email;
    }

    public String getRole() {
      return this.role;
    }

}
