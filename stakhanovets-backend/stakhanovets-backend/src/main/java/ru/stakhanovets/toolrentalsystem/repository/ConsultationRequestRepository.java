package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.ConsultationRequest;

public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Long> {
}
