package com.bookingsystem.booking.user.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.bookingsystem.booking.user.api.dtos.response.UserDTO;
import com.bookingsystem.booking.user.domain.entities.User;

public class UserMapper {
    
    public static UserDTO toDto(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }

    public static List<UserDTO> toDtoList(List<User> users) {
        return users.stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
    }

}
