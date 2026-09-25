package com.edusupport_backend.edusupport_backend.DTO;

import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Enums.TicketCategory;
import com.edusupport_backend.edusupport_backend.Enums.TicketStatus;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private Long id;

    private String ticketNumber;

    private String title;

    private String description;

    private TicketCategory category;

    private Priority priority;

    private TicketStatus status;

    private Long createdById;

    private String createdByName;

    private Long assignedToId;

    private String assignedToName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private LocalDateTime resolvedAt;

    private LocalDateTime closedAt;
}