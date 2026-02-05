package ru.stakhanovets.toolrentalsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateRentalRequest {
    @NotNull
    private Long clientId;

    @NotNull
    private Long managerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotEmpty
    private List<Long> toolIds;
}
