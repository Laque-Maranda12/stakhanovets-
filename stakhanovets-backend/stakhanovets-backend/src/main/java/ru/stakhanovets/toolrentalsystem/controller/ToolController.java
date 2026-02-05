package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.StatusRequest;
import ru.stakhanovets.toolrentalsystem.dto.ToolDto;
import ru.stakhanovets.toolrentalsystem.service.ToolService;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
@CrossOrigin
public class ToolController {

    private final ToolService toolService;

    @GetMapping
    public List<ToolDto> getAllTools() {
        return toolService.getAll();
    }

    @GetMapping(params = "categoryId")
    public List<ToolDto> getByCategory(@RequestParam Long categoryId) {
        return toolService.getByCategory(categoryId);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        toolService.updateStatus(id, request.status());
    }
}
