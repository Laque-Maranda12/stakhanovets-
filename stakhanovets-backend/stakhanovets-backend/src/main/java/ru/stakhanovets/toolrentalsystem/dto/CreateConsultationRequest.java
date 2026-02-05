package ru.stakhanovets.toolrentalsystem.dto;

public record CreateConsultationRequest(
        String clientName,
        String clientPhone,
        String clientEmail,
        String message
) {}
