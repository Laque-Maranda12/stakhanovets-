package ru.stakhanovets.toolrentalsystem.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.CreateRentalRequest;
import ru.stakhanovets.toolrentalsystem.model.RentalOrder;
import ru.stakhanovets.toolrentalsystem.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public RentalOrder create(@Valid @RequestBody CreateRentalRequest req) {
        return rentalService.createRental(req);
    }

    @PostMapping("/{id}/issue")
    public RentalOrder issue(@PathVariable Long id) {
        return rentalService.issue(id);
    }

    @PostMapping("/{id}/close")
    public RentalOrder close(@PathVariable Long id) {
        return rentalService.close(id);
    }
}
