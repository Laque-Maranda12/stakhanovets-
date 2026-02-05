package ru.stakhanovets.toolrentalsystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateRentalRequest {
    private Long clientId;
    private Long managerId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotEmpty
    private List<Long> toolIds;

    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private String comment;
}
