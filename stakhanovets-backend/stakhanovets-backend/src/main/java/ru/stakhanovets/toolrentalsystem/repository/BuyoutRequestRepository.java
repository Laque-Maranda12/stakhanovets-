package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.BuyoutRequest;

public interface BuyoutRequestRepository extends JpaRepository<BuyoutRequest, Long> {
}
