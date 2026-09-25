package com.edusupport_backend.edusupport_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ticket associated with this comment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    // User who created the comment
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Comment text
    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    // Comment creation time
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Comment update time
    private LocalDateTime updatedAt;
}