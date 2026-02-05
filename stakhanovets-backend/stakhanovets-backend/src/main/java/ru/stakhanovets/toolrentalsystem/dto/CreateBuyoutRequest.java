package ru.stakhanovets.toolrentalsystem.dto;

import java.math.BigDecimal;

public record CreateBuyoutRequest(
        Long toolId,
        BigDecimal offeredPrice,
        String clientName,
        String clientPhone
) {}
