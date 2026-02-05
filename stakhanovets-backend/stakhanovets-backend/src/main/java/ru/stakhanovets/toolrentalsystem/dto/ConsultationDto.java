package ru.stakhanovets.toolrentalsystem.dto;

public record ConsultationDto(
        long id,
        String clientName,
        String clientPhone,
        String clientEmail,
        String message,
        String status,
        String createdAt
) {}
