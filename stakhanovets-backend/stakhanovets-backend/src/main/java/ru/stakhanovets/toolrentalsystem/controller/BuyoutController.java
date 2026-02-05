package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.BuyoutDto;
import ru.stakhanovets.toolrentalsystem.dto.CreateBuyoutRequest;
import ru.stakhanovets.toolrentalsystem.model.BuyoutRequest;
import ru.stakhanovets.toolrentalsystem.service.BuyoutService;

import java.util.List;

@RestController
@RequestMapping("/api/buyouts")
@CrossOrigin
@RequiredArgsConstructor
public class BuyoutController {

    private final BuyoutService buyoutService;

    @PostMapping
    public BuyoutRequest create(@RequestBody CreateBuyoutRequest request) {
        return buyoutService.create(request);
    }

    @GetMapping
    public List<BuyoutDto> getAll() {
        return buyoutService.getAll();
    }
}
