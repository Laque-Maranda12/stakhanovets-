package ru.stakhanovets.toolrentalsystem.dto;

import java.math.BigDecimal;

public record BuyoutDto(
        long id,
        String toolName,
        String clientName,
        String clientPhone,
        BigDecimal offeredPrice,
        String status,
        String createdAt
) {}
