package com.example.pizzeria.mapper;

import com.example.pizzeria.domain.entity.User;
import com.example.pizzeria.dto.UserDto;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
    dto.setId(user.getPublicId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }
}
