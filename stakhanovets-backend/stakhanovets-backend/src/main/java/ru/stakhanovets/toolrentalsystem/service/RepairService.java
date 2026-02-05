package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.CreateRepairRequest;
import ru.stakhanovets.toolrentalsystem.dto.RepairDto;
import ru.stakhanovets.toolrentalsystem.model.RepairRequest;
import ru.stakhanovets.toolrentalsystem.model.RepairStatus;
import ru.stakhanovets.toolrentalsystem.model.Tool;
import ru.stakhanovets.toolrentalsystem.repository.RepairRequestRepository;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRequestRepository repairRequestRepository;
    private final ToolRepository toolRepository;

    public List<RepairDto> getAll() {
        return repairRequestRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public RepairRequest create(CreateRepairRequest request) {
        Tool tool = toolRepository.findById(request.toolId())
                .orElseThrow(() -> new IllegalArgumentException("Инструмент не найден: " + request.toolId()));

        RepairRequest repair = new RepairRequest();
        repair.setTool(tool);
        repair.setDescription(request.description());
        repair.setClientName(request.clientName());
        repair.setClientPhone(request.clientPhone());
        repair.setStatus(RepairStatus.REQUESTED);
        repair.setCost(BigDecimal.ZERO);
        repair.setCreatedAt(LocalDateTime.now());

        return repairRequestRepository.save(repair);
    }

    public void updateStatus(Long id, String status) {
        RepairRequest repair = repairRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка на ремонт не найдена: " + id));
        repair.setStatus(RepairStatus.valueOf(status));
        repairRequestRepository.save(repair);
    }

    public void updateCost(Long id, int cost) {
        RepairRequest repair = repairRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка на ремонт не найдена: " + id));
        repair.setCost(BigDecimal.valueOf(cost));
        repairRequestRepository.save(repair);
    }

    private RepairDto toDto(RepairRequest r) {
        return new RepairDto(
                r.getId(),
                r.getTool() != null ? r.getTool().getName() : "—",
                r.getCreatedAt() != null ? r.getCreatedAt().toString() : "",
                r.getCost() != null ? r.getCost().intValue() : 0,
                r.getStatus().name()
        );
    }
}
