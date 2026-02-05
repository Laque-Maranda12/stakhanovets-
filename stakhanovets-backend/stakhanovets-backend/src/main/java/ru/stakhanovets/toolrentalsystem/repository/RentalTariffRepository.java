package ru.stakhanovets.toolrentalsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stakhanovets.toolrentalsystem.model.RentalTariff;

public interface RentalTariffRepository extends JpaRepository<RentalTariff, Long> {
}
