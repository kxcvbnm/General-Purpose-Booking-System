package com.bookingsystem.booking.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.bookingsystem.booking.dto.UserDTO;
import com.bookingsystem.booking.models.User;

public class UserMapper {
    
    public static UserDTO toDto(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
    }

    public static List<UserDTO> toDtoList(List<User> users) {
        return users.stream()
                    .map(UserMapper::toDto)
                    .collect(Collectors.toList());
    }

}
