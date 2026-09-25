package com.edusupport_backend.edusupport_backend.Services.Impl;

import com.edusupport_backend.edusupport_backend.Entity.Sla;
import com.edusupport_backend.edusupport_backend.Entity.Ticket;
import com.edusupport_backend.edusupport_backend.Enums.TicketStatus;
import com.edusupport_backend.edusupport_backend.Repository.SlaRepository;
import com.edusupport_backend.edusupport_backend.Repository.TicketRepository;
import com.edusupport_backend.edusupport_backend.Services.SlaService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SlaServiceImpl implements SlaService {

    private final SlaRepository slaRepository;
    private final TicketRepository ticketRepository;

    public SlaServiceImpl(
            SlaRepository slaRepository,
            TicketRepository ticketRepository) {

        this.slaRepository = slaRepository;
        this.ticketRepository = ticketRepository;
    }

    // =========================================================
    // CREATE SLA
    // =========================================================

    @Override
    public Sla createSla(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found with id: " + ticketId
                        )
                );

        // Prevent duplicate SLA
        if (slaRepository.findByTicketId(ticketId).isPresent()) {

            throw new RuntimeException(
                    "SLA already exists for ticket: " + ticketId
            );
        }

        LocalDateTime createdAt =
                ticket.getCreatedAt();

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        /*
         * Default SLA:
         *
         * Response time  : 4 hours
         * Resolution time: 24 hours
         */

        LocalDateTime responseDueAt =
                createdAt.plusHours(4);

        LocalDateTime resolutionDueAt =
                createdAt.plusHours(24);

        Sla sla = Sla.builder()
                .ticket(ticket)
                .responseTimeHours(4)
                .resolutionTimeHours(24)
                .responseDueAt(responseDueAt)
                .resolutionDueAt(resolutionDueAt)
                .breached(false)
                .build();

        return slaRepository.save(sla);
    }


    // =========================================================
    // GET SLA BY TICKET
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Sla getSlaByTicketId(Long ticketId) {

        return slaRepository.findByTicketId(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SLA not found for ticket: "
                                        + ticketId
                        )
                );
    }


    // =========================================================
    // GET ALL SLAS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Sla> getAllSlas() {

        return slaRepository.findAll();
    }


    // =========================================================
    // UPDATE SLA
    // =========================================================

    @Override
    public Sla updateSla(Long ticketId) {

        Sla sla = slaRepository.findByTicketId(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SLA not found for ticket: "
                                        + ticketId
                        )
                );

        Ticket ticket = sla.getTicket();

        LocalDateTime createdAt =
                ticket.getCreatedAt();

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        sla.setResponseDueAt(
                createdAt.plusHours(
                        sla.getResponseTimeHours()
                )
        );

        sla.setResolutionDueAt(
                createdAt.plusHours(
                        sla.getResolutionTimeHours()
                )
        );

        return slaRepository.save(sla);
    }


    // =========================================================
    // DELETE SLA
    // =========================================================

    @Override
    public void deleteSla(Long ticketId) {

        Sla sla = slaRepository.findByTicketId(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "SLA not found for ticket: "
                                        + ticketId
                        )
                );

        slaRepository.delete(sla);
    }


    // =========================================================
    // CHECK SLA BREACHES
    // =========================================================

    @Override
    public void checkSlaBreaches() {

        List<Sla> slas =
                slaRepository.findAll();

        LocalDateTime now =
                LocalDateTime.now();

        for (Sla sla : slas) {

            Ticket ticket =
                    sla.getTicket();

            if (ticket == null) {
                continue;
            }

            // Already resolved/closed tickets
            if (ticket.getStatus() == TicketStatus.RESOLVED
                    || ticket.getStatus() == TicketStatus.CLOSED) {

                continue;
            }

            boolean resolutionBreached =
                    now.isAfter(
                            sla.getResolutionDueAt()
                    );

            if (resolutionBreached) {

                sla.setBreached(true);

                slaRepository.save(sla);
            }
        }
    }
}