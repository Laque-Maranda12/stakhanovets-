package ru.stakhanovets.toolrentalsystem.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.CalculateRequest;
import ru.stakhanovets.toolrentalsystem.dto.CalculateResponse;
import ru.stakhanovets.toolrentalsystem.dto.CreateRentalRequest;
import ru.stakhanovets.toolrentalsystem.dto.RentalDto;
import ru.stakhanovets.toolrentalsystem.model.*;
import ru.stakhanovets.toolrentalsystem.repository.RentalOrderRepository;
import ru.stakhanovets.toolrentalsystem.repository.ToolRepository;
import ru.stakhanovets.toolrentalsystem.repository.UserRepository;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalOrderRepository rentalOrderRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    public List<RentalDto> getAllDto() {
        return rentalOrderRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public RentalOrder createRental(CreateRentalRequest req, Principal principal) {
        List<Tool> tools = toolRepository.findAllById(req.getToolIds());

        if (tools.size() != req.getToolIds().size()) {
            throw new IllegalArgumentException("Некоторые инструменты не найдены");
        }

        Long clientId = req.getClientId();
        if (clientId == null && principal != null) {
            User user = userRepository.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                clientId = user.getId();
            }
        }

        RentalOrder order = new RentalOrder();
        order.setClientId(clientId);
        order.setManagerId(req.getManagerId());
        order.setStartDate(req.getStartDate());
        order.setEndDate(req.getEndDate());
        order.setStatus(RentalStatus.CREATED);
        order.setComment(req.getComment());
        order.setCreatedAt(LocalDateTime.now());

        int days = (int) ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate());
        if (days <= 0) days = 1;

        BigDecimal total = BigDecimal.ZERO;
        for (Tool tool : tools) {
            RentalItem item = new RentalItem();
            item.setTool(tool);
            order.addItem(item);
            int ppd = tool.getPricePerDay() != null ? tool.getPricePerDay() : 0;
            total = total.add(BigDecimal.valueOf((long) ppd * days));
        }
        order.setTotalCost(total);

        return rentalOrderRepository.save(order);
    }

    @Transactional
    public void approve(Long rentalId) {
        RentalOrder order = rentalOrderRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Аренда не найдена: id=" + rentalId));

        if (order.getStatus() != RentalStatus.CREATED) {
            throw new IllegalStateException("Нельзя одобрить аренду со статусом " + order.getStatus());
        }

        order.setStatus(RentalStatus.APPROVED);
        rentalOrderRepository.save(order);
    }

    @Transactional
    public RentalOrder issue(Long rentalId) {
        RentalOrder order = rentalOrderRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Аренда не найдена: id=" + rentalId));

        if (order.getStatus() != RentalStatus.CREATED && order.getStatus() != RentalStatus.APPROVED) {
            throw new IllegalStateException("Нельзя выдать аренду со статусом " + order.getStatus());
        }

        for (RentalItem item : order.getItems()) {
            Tool tool = item.getTool();
            if (tool.getStatus() != ToolStatus.AVAILABLE) {
                throw new IllegalStateException("Инструмент недоступен: id=" + tool.getId() + ", статус=" + tool.getStatus());
            }
            tool.setStatus(ToolStatus.RENTED);
        }

        order.setStatus(RentalStatus.ISSUED);
        order.setIssuedAt(LocalDateTime.now());
        return rentalOrderRepository.save(order);
    }

    @Transactional
    public RentalOrder returnRental(Long rentalId) {
        RentalOrder order = rentalOrderRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Аренда не найдена: id=" + rentalId));

        if (order.getStatus() != RentalStatus.ISSUED && order.getStatus() != RentalStatus.ACTIVE) {
            throw new IllegalStateException("Нельзя закрыть аренду со статусом " + order.getStatus());
        }

        for (RentalItem item : order.getItems()) {
            item.getTool().setStatus(ToolStatus.AVAILABLE);
        }

        order.setStatus(RentalStatus.RETURNED);
        order.setReturnedAt(LocalDateTime.now());
        return rentalOrderRepository.save(order);
    }

    public CalculateResponse calculate(CalculateRequest request) {
        List<Tool> tools = toolRepository.findAllById(request.toolIds());
        int days = request.days() > 0 ? request.days() : 1;

        List<CalculateResponse.CalculateItemDto> items = new ArrayList<>();
        int totalPrice = 0;
        int totalDeposit = 0;

        for (Tool tool : tools) {
            int ppd = tool.getPricePerDay() != null ? tool.getPricePerDay() : 0;
            int dep = tool.getDeposit() != null ? tool.getDeposit() : 0;
            int subtotal = ppd * days;
            totalPrice += subtotal;
            totalDeposit += dep;
            items.add(new CalculateResponse.CalculateItemDto(
                    tool.getId(), tool.getName(), ppd, days, subtotal, dep
            ));
        }

        return new CalculateResponse(totalPrice, totalDeposit, items);
    }

    private RentalDto toDto(RentalOrder order) {
        String toolName = order.getItems().stream()
                .map(item -> item.getTool().getName())
                .collect(Collectors.joining(", "));
        int days = (int) ChronoUnit.DAYS.between(order.getStartDate(), order.getEndDate());
        if (days <= 0) days = 1;
        return new RentalDto(
                order.getId(),
                toolName,
                order.getStartDate().toString(),
                days,
                order.getTotalCost().intValue(),
                order.getStatus().name()
        );
    }
}
