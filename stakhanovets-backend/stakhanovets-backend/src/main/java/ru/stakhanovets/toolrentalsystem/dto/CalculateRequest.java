package ru.stakhanovets.toolrentalsystem.dto;

import java.util.List;

public record CalculateRequest(List<Long> toolIds, int days) {}
