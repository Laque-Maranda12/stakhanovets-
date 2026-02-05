package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
