package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.ToolDto;
import ru.stakhanovets.toolrentalsystem.model.Tool;
import ru.stakhanovets.toolrentalsystem.model.ToolStatus;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;

    public List<ToolDto> getAll() {
        return toolRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<ToolDto> getByCategory(Long categoryId) {
        return toolRepository.findByCategoryId(categoryId).stream()
                .map(this::toDto)
                .toList();
    }

    public void updateStatus(Long id, String status) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Инструмент не найден: " + id));
        tool.setStatus(ToolStatus.valueOf(status));
        toolRepository.save(tool);
    }

    private ToolDto toDto(Tool tool) {
        return new ToolDto(
                tool.getId(),
                tool.getName(),
                tool.getSerialNumber(),
                tool.getStatus().name(),
                tool.getPurchasePrice(),
                tool.getPricePerDay() != null ? tool.getPricePerDay() : 0,
                tool.getDeposit() != null ? tool.getDeposit() : 0,
                tool.getStock() != null ? tool.getStock() : 1,
                tool.getDescription(),
                tool.getImageUrl(),
                tool.getCategory() != null ? tool.getCategory().getName() : null
        );
    }
}
