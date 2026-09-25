package com.edusupport_backend.edusupport_backend.Repository;

import com.edusupport_backend.edusupport_backend.Entity.Ticket;
import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Enums.TicketStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    List<Ticket> findByPriority(Priority priority);

    long countByPriority(Priority priority);

    long countByStatus(TicketStatus status);

    long countByCreatedById(Long userId);

    long countByAssignedToId(Long userId);

    List<Ticket> findByCreatedById(Long userId);

    List<Ticket> findByAssignedToId(Long userId);
}