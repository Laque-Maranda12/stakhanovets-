package ru.stakhanovets.toolrentalsystem.dto;

public record RepairDto(
        long id,
        String toolName,
        String createdAt,
        int cost,
        String status
) {}
