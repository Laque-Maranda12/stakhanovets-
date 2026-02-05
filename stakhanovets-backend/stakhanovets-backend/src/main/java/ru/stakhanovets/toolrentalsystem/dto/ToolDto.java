package ru.stakhanovets.toolrentalsystem.dto;

import java.math.BigDecimal;

public record ToolDto(
        Long id,
        String name,
        String serialNumber,
        String status,
        BigDecimal purchasePrice,
        int pricePerDay,
        int deposit,
        int stock,
        String description,
        String imageUrl,
        String categoryName
) {}
