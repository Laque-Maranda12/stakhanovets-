package ru.stakhanovets.toolrentalsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rental_items")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", nullable = false)
    private RentalOrder rentalOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id", nullable = false)
    private Tool tool;
}
