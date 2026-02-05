package ru.stakhanovets.toolrentalsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.CalculateRequest;
import ru.stakhanovets.toolrentalsystem.dto.CalculateResponse;
import ru.stakhanovets.toolrentalsystem.dto.CreateRentalRequest;
import ru.stakhanovets.toolrentalsystem.dto.RentalDto;
import ru.stakhanovets.toolrentalsystem.model.RentalOrder;
import ru.stakhanovets.toolrentalsystem.service.RentalService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public List<RentalDto> getAll() {
        return rentalService.getAllDto();
    }

    @PostMapping
    public RentalOrder create(@Valid @RequestBody CreateRentalRequest req, Principal principal) {
        return rentalService.createRental(req, principal);
    }

    @PutMapping("/{id}/approve")
    public void approve(@PathVariable Long id) {
        rentalService.approve(id);
    }

    @PutMapping("/{id}/issue")
    public void issue(@PathVariable Long id) {
        rentalService.issue(id);
    }

    @PutMapping("/{id}/return")
    public void returnRental(@PathVariable Long id) {
        rentalService.returnRental(id);
    }

    @PostMapping("/calculate")
    public CalculateResponse calculate(@RequestBody CalculateRequest request) {
        return rentalService.calculate(request);
    }
}
