package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.CategoryDto;
import ru.stakhanovets.toolrentalsystem.dto.ToolDto;
import ru.stakhanovets.toolrentalsystem.model.Tool;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
@CrossOrigin
public class ToolController {

    private final ToolRepository toolRepository;

    @GetMapping
    public List<ToolDto> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ToolDto toDto(Tool tool) {
        return new ToolDto(
                tool.getId(),
                tool.getName(),
                tool.getSerialNumber(),
                tool.getStatus().name(),
                tool.getPurchasePrice(),
                new CategoryDto(tool.getCategory().getId(), tool.getCategory().getName())
        );
    }
}
