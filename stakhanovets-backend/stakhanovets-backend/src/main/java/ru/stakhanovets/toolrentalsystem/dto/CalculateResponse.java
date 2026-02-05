package ru.stakhanovets.toolrentalsystem.dto;

import java.util.List;

public record CalculateResponse(
        int totalPrice,
        int deposit,
        List<CalculateItemDto> items
) {
    public record CalculateItemDto(
            Long toolId,
            String name,
            int pricePerDay,
            int days,
            int subtotal,
            int deposit
    ) {}
}
