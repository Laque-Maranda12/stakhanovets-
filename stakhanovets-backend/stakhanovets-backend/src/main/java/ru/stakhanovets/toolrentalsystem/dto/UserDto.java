package ru.stakhanovets.toolrentalsystem.dto;

import java.util.List;

public record UserDto(
        long id,
        String fullName,
        String email,
        boolean active,
        List<String> roles
) {}
