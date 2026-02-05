package ru.stakhanovets.toolrentalsystem.dto;

public record RentalDto(
        long id,
        String toolName,
        String startDate,
        int days,
        int sum,
        String status
) {}
