package com.bookingsystem.booking.user.api.dtos.response;

import com.bookingsystem.booking.user.domain.enums.Role;

public class UserDTO {

    private final Long id;
    private final String username;
    private final String email;
    private final Role role;

    public UserDTO(Long id, String username, String email, Role role) {
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

    public Role getRole() {
      return this.role;
    }

}
