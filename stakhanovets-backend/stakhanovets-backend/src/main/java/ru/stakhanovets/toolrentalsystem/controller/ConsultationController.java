package ru.stakhanovets.toolrentalsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.stakhanovets.toolrentalsystem.dto.ConsultationDto;
import ru.stakhanovets.toolrentalsystem.dto.CreateConsultationRequest;
import ru.stakhanovets.toolrentalsystem.model.ConsultationRequest;
import ru.stakhanovets.toolrentalsystem.service.ConsultationService;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@CrossOrigin
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ConsultationRequest create(@RequestBody CreateConsultationRequest request) {
        return consultationService.create(request);
    }

    @GetMapping
    public List<ConsultationDto> getAll() {
        return consultationService.getAll();
    }
}
