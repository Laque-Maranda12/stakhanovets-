package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.CostRequest;
import ru.stakhanovets.toolrentalsystem.dto.CreateRepairRequest;
import ru.stakhanovets.toolrentalsystem.dto.RepairDto;
import ru.stakhanovets.toolrentalsystem.dto.StatusRequest;
import ru.stakhanovets.toolrentalsystem.model.RepairRequest;
import ru.stakhanovets.toolrentalsystem.service.RepairService;

import java.util.List;

@RestController
@RequestMapping("/api/repairs")
@CrossOrigin
@RequiredArgsConstructor
public class RepairController {

    private final RepairService repairService;

    @GetMapping
    public List<RepairDto> getAll() {
        return repairService.getAll();
    }

    @PostMapping
    public RepairRequest create(@RequestBody CreateRepairRequest request) {
        return repairService.create(request);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id, @RequestBody StatusRequest request) {
        repairService.updateStatus(id, request.status());
    }

    @PutMapping("/{id}/cost")
    public void updateCost(@PathVariable Long id, @RequestBody CostRequest request) {
        repairService.updateCost(id, request.cost());
    }
}
