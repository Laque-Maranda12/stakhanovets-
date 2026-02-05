package ru.stakhanovets.toolrentalsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rental_orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RentalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "manager_id", nullable = false)
    private Long managerId;

    @Column(name = "tariff_id")
    private Long tariffId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status = RentalStatus.CREATED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "rentalOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalItem> items = new ArrayList<>();

    public void addItem(RentalItem item) {
        items.add(item);
        item.setRentalOrder(this);
    }
}
