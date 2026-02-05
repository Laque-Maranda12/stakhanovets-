package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.BuyoutDto;
import ru.stakhanovets.toolrentalsystem.dto.CreateBuyoutRequest;
import ru.stakhanovets.toolrentalsystem.model.BuyoutRequest;
import ru.stakhanovets.toolrentalsystem.model.BuyoutStatus;
import ru.stakhanovets.toolrentalsystem.model.Tool;
import ru.stakhanovets.toolrentalsystem.repository.BuyoutRequestRepository;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyoutService {

    private final BuyoutRequestRepository buyoutRequestRepository;
    private final ToolRepository toolRepository;

    public List<BuyoutDto> getAll() {
        return buyoutRequestRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public BuyoutRequest create(CreateBuyoutRequest request) {
        Tool tool = toolRepository.findById(request.toolId())
                .orElseThrow(() -> new IllegalArgumentException("Инструмент не найден: " + request.toolId()));

        BuyoutRequest buyout = new BuyoutRequest();
        buyout.setTool(tool);
        buyout.setClientName(request.clientName());
        buyout.setClientPhone(request.clientPhone());
        buyout.setOfferedPrice(request.offeredPrice());
        buyout.setStatus(BuyoutStatus.REQUESTED);
        buyout.setCreatedAt(LocalDateTime.now());

        return buyoutRequestRepository.save(buyout);
    }

    public void updateStatus(Long id, String status) {
        BuyoutRequest buyout = buyoutRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заявка на выкуп не найдена: " + id));
        buyout.setStatus(BuyoutStatus.valueOf(status));
        buyoutRequestRepository.save(buyout);
    }

    private BuyoutDto toDto(BuyoutRequest b) {
        return new BuyoutDto(
                b.getId(),
                b.getTool() != null ? b.getTool().getName() : "—",
                b.getClientName(),
                b.getClientPhone(),
                b.getOfferedPrice(),
                b.getStatus().name(),
                b.getCreatedAt() != null ? b.getCreatedAt().toString() : ""
        );
    }
}
