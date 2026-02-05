package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.RepairRequest;

public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
}
