package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.RentalOrder;

public interface RentalOrderRepository extends JpaRepository<RentalOrder, Long> {}
