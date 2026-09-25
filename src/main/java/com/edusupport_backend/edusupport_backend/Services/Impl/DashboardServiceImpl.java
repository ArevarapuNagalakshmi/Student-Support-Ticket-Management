package com.edusupport_backend.edusupport_backend.Services.Impl;

import com.edusupport_backend.edusupport_backend.DTO.DashboardResponse;
import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Enums.TicketStatus;
import com.edusupport_backend.edusupport_backend.Repository.SlaRepository;
import com.edusupport_backend.edusupport_backend.Repository.TicketRepository;
import com.edusupport_backend.edusupport_backend.Services.DashboardService;

import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final TicketRepository ticketRepository;
    private final SlaRepository slaRepository;

    public DashboardServiceImpl(
            TicketRepository ticketRepository,
            SlaRepository slaRepository) {

        this.ticketRepository = ticketRepository;
        this.slaRepository = slaRepository;
    }

    @Override
    public DashboardResponse getDashboard() {

        long totalTickets =
                ticketRepository.count();

        long openTickets =
                ticketRepository.countByStatus(
                        TicketStatus.OPEN);

        long assignedTickets =
                ticketRepository.countByStatus(
                        TicketStatus.ASSIGNED);

        long inProgressTickets =
                ticketRepository.countByStatus(
                        TicketStatus.IN_PROGRESS);

        long pendingTickets =
                ticketRepository.countByStatus(
                        TicketStatus.PENDING);

        long resolvedTickets =
                ticketRepository.countByStatus(
                        TicketStatus.RESOLVED);

        long closedTickets =
                ticketRepository.countByStatus(
                        TicketStatus.CLOSED);

        long overdueTickets =
                slaRepository.countByBreachedTrue();

        long criticalTickets =
                ticketRepository.countByPriority(
                        Priority.CRITICAL);

        return DashboardResponse.builder()
                .totalTickets(totalTickets)
                .openTickets(openTickets)
                .assignedTickets(assignedTickets)
                .inProgressTickets(inProgressTickets)
                .pendingTickets(pendingTickets)
                .resolvedTickets(resolvedTickets)
                .closedTickets(closedTickets)
                .overdueTickets(overdueTickets)
                .criticalTickets(criticalTickets)
                .build();
    }

    @Override
    public DashboardResponse getUserDashboard(
            Long userId) {

        long totalTickets =
                ticketRepository.countByCreatedById(userId);

        long assignedTickets =
                ticketRepository.countByAssignedToId(userId);

        long openTickets =
                ticketRepository.findByCreatedById(userId)
                        .stream()
                        .filter(ticket ->
                                ticket.getStatus()
                                        == TicketStatus.OPEN)
                        .count();

        long inProgressTickets =
                ticketRepository.findByCreatedById(userId)
                        .stream()
                        .filter(ticket ->
                                ticket.getStatus()
                                        == TicketStatus.IN_PROGRESS)
                        .count();

        long pendingTickets =
                ticketRepository.findByCreatedById(userId)
                        .stream()
                        .filter(ticket ->
                                ticket.getStatus()
                                        == TicketStatus.PENDING)
                        .count();

        long resolvedTickets =
                ticketRepository.findByCreatedById(userId)
                        .stream()
                        .filter(ticket ->
                                ticket.getStatus()
                                        == TicketStatus.RESOLVED)
                        .count();

        long closedTickets =
                ticketRepository.findByCreatedById(userId)
                        .stream()
                        .filter(ticket ->
                                ticket.getStatus()
                                        == TicketStatus.CLOSED)
                        .count();

        return DashboardResponse.builder()
                .totalTickets(totalTickets)
                .openTickets(openTickets)
                .assignedTickets(assignedTickets)
                .inProgressTickets(inProgressTickets)
                .pendingTickets(pendingTickets)
                .resolvedTickets(resolvedTickets)
                .closedTickets(closedTickets)
                .overdueTickets(0L)
                .criticalTickets(0L)
                .build();
    }
}