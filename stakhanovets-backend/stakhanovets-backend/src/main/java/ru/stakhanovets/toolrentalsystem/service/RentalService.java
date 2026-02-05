package ru.stakhanovets.toolrentalsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.CreateRentalRequest;
import ru.stakhanovets.toolrentalsystem.model.*;
import ru.stakhanovets.toolrentalsystem.repository.RentalOrderRepository;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentalService {

    private final RentalOrderRepository rentalOrderRepository;
    private final ToolRepository toolRepository;

    public RentalService(RentalOrderRepository rentalOrderRepository, ToolRepository toolRepository) {
        this.rentalOrderRepository = rentalOrderRepository;
        this.toolRepository = toolRepository;
    }

    // 1) создаём заявку (статусы инструментов не меняем)
    @Transactional
    public RentalOrder createRental(CreateRentalRequest req) {
        List<Tool> tools = toolRepository.findAllById(req.getToolIds());

        if (tools.size() != req.getToolIds().size()) {
            throw new IllegalArgumentException("Некоторые инструменты не найдены");
        }

        RentalOrder order = new RentalOrder();
        order.setClientId(req.getClientId());
        order.setManagerId(req.getManagerId());
        order.setStartDate(req.getStartDate());
        order.setEndDate(req.getEndDate());
        order.setStatus(RentalStatus.CREATED);
        order.setTotalCost(BigDecimal.ZERO);
        order.setCreatedAt(LocalDateTime.now());

        for (Tool tool : tools) {
            RentalItem item = new RentalItem();
            item.setTool(tool);
            order.addItem(item);
        }

        return rentalOrderRepository.save(order);
    }

    // 2) выдача (инструменты -> RENTED)
    @Transactional
    public RentalOrder issue(Long rentalId) {
        RentalOrder order = rentalOrderRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Аренда не найдена: id=" + rentalId));

        if (order.getStatus() != RentalStatus.CREATED) {
            throw new IllegalStateException("Нельзя выдать аренду со статусом " + order.getStatus());
        }

        for (RentalItem item : order.getItems()) {
            Tool tool = item.getTool();
            if (tool.getStatus() != ToolStatus.AVAILABLE) {
                throw new IllegalStateException("Инструмент недоступен: id=" + tool.getId() + ", статус=" + tool.getStatus());
            }
            tool.setStatus(ToolStatus.RENTED);
        }

        order.setStatus(RentalStatus.ACTIVE);
        order.setIssuedAt(LocalDateTime.now());
        return rentalOrderRepository.save(order);
    }

    // 3) закрытие (инструменты -> AVAILABLE)
    @Transactional
    public RentalOrder close(Long rentalId) {
        RentalOrder order = rentalOrderRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Аренда не найдена: id=" + rentalId));

        if (order.getStatus() != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Нельзя закрыть аренду со статусом " + order.getStatus());
        }

        for (RentalItem item : order.getItems()) {
            item.getTool().setStatus(ToolStatus.AVAILABLE);
        }

        order.setStatus(RentalStatus.CLOSED);
        order.setReturnedAt(LocalDateTime.now());
        return rentalOrderRepository.save(order);
    }
}
