package ru.stakhanovets.toolrentalsystem.dto;

public record CreateRepairRequest(
        Long toolId,
        String description,
        String clientName,
        String clientPhone
) {}
