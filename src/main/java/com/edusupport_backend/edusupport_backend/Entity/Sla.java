package com.edusupport_backend.edusupport_backend.Entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "slas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ticket_id",
            nullable = false,
            unique = true
    )
    private Ticket ticket;

    @Column(nullable = false)
    private Integer responseTimeHours;

    @Column(nullable = false)
    private Integer resolutionTimeHours;

    @Column(nullable = false)
    private LocalDateTime responseDueAt;

    @Column(nullable = false)
    private LocalDateTime resolutionDueAt;

    @Column(nullable = false)
    private Boolean breached = false;
}