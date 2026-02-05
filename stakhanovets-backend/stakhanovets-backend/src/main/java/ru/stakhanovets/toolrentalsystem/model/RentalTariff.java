package ru.stakhanovets.toolrentalsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "rental_tariffs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalTariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "price_per_day", nullable = false)
    private BigDecimal pricePerDay;

    @Column(name = "deposit_percent")
    private BigDecimal depositPercent;

    @Column(name = "min_days")
    private Integer minDays;
}
