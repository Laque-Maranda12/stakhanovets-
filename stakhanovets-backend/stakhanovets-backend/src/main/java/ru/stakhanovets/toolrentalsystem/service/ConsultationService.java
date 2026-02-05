package ru.stakhanovets.toolrentalsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stakhanovets.toolrentalsystem.dto.ConsultationDto;
import ru.stakhanovets.toolrentalsystem.dto.CreateConsultationRequest;
import ru.stakhanovets.toolrentalsystem.model.ConsultationRequest;
import ru.stakhanovets.toolrentalsystem.model.ConsultationStatus;
import ru.stakhanovets.toolrentalsystem.repository.ConsultationRequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRequestRepository consultationRequestRepository;

    public List<ConsultationDto> getAll() {
        return consultationRequestRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public ConsultationRequest create(CreateConsultationRequest request) {
        ConsultationRequest consultation = new ConsultationRequest();
        consultation.setClientName(request.clientName());
        consultation.setClientPhone(request.clientPhone());
        consultation.setClientEmail(request.clientEmail());
        consultation.setMessage(request.message());
        consultation.setStatus(ConsultationStatus.NEW);
        consultation.setCreatedAt(LocalDateTime.now());

        return consultationRequestRepository.save(consultation);
    }

    private ConsultationDto toDto(ConsultationRequest c) {
        return new ConsultationDto(
                c.getId(),
                c.getClientName(),
                c.getClientPhone(),
                c.getClientEmail(),
                c.getMessage(),
                c.getStatus().name(),
                c.getCreatedAt() != null ? c.getCreatedAt().toString() : ""
        );
    }
}
