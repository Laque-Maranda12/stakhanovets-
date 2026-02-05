package ru.stakhanovets.toolrentalsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "buyout_requests")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BuyoutRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private Tool tool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_phone")
    private String clientPhone;

    @Column(name = "offered_price")
    private BigDecimal offeredPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuyoutStatus status = BuyoutStatus.REQUESTED;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
