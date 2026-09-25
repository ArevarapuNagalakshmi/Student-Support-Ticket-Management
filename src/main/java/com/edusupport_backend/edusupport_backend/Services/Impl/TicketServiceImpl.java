package com.edusupport_backend.edusupport_backend.Services.Impl;

import com.edusupport_backend.edusupport_backend.DTO.TicketRequest;
import com.edusupport_backend.edusupport_backend.DTO.TicketResponse;
import com.edusupport_backend.edusupport_backend.Entity.Ticket;
import com.edusupport_backend.edusupport_backend.Entity.User;
import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Enums.TicketStatus;
import com.edusupport_backend.edusupport_backend.Repository.TicketRepository;
import com.edusupport_backend.edusupport_backend.Repository.UserRepository;
import com.edusupport_backend.edusupport_backend.Services.TicketService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(
            TicketRepository ticketRepository,
            UserRepository userRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // CREATE TICKET
    // =========================================================

    @Override
    public TicketResponse createTicket(
            TicketRequest request,
            Long userId) {

        User createdBy = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + userId
                        )
                );

        Ticket ticket = new Ticket();

        ticket.setTicketNumber(
                generateTicketNumber()
        );

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());

        ticket.setStatus(TicketStatus.OPEN);

        ticket.setCreatedBy(createdBy);

        ticket.setCreatedAt(
                LocalDateTime.now()
        );

        ticket.setUpdatedAt(
                LocalDateTime.now()
        );

        // Assign ticket if assignedTo is provided
        if (request.getAssignedTo() != null) {

            User assignedUser =
                    userRepository.findById(
                            request.getAssignedTo()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Assigned user not found with id: "
                                            + request.getAssignedTo()
                            )
                    );

            ticket.setAssignedTo(assignedUser);

            ticket.setStatus(
                    TicketStatus.ASSIGNED
            );
        }

        Ticket savedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(savedTicket);
    }


    // =========================================================
    // GET ALL TICKETS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GET TICKET BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicketById(
            Long id) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found with id: "
                                                + id
                                )
                        );

        return convertToResponse(ticket);
    }


    // =========================================================
    // UPDATE TICKET
    // =========================================================

    @Override
    public TicketResponse updateTicket(
            Long id,
            TicketRequest request) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found with id: "
                                                + id
                                )
                        );

        ticket.setTitle(
                request.getTitle()
        );

        ticket.setDescription(
                request.getDescription()
        );

        ticket.setCategory(
                request.getCategory()
        );

        ticket.setPriority(
                request.getPriority()
        );

        ticket.setUpdatedAt(
                LocalDateTime.now()
        );

        // Update assigned user
        if (request.getAssignedTo() != null) {

            User assignedUser =
                    userRepository.findById(
                            request.getAssignedTo()
                    ).orElseThrow(() ->
                            new RuntimeException(
                                    "Assigned user not found with id: "
                                            + request.getAssignedTo()
                            )
                    );

            ticket.setAssignedTo(assignedUser);

            if (ticket.getStatus() == TicketStatus.OPEN) {
                ticket.setStatus(
                        TicketStatus.ASSIGNED
                );
            }

        } else {

            ticket.setAssignedTo(null);
        }

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(updatedTicket);
    }


    // =========================================================
    // DELETE TICKET
    // =========================================================

    @Override
    public void deleteTicket(
            Long id) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found with id: "
                                                + id
                                )
                        );

        ticketRepository.delete(ticket);
    }


    // =========================================================
    // UPDATE STATUS
    // =========================================================

    @Override
    public TicketResponse updateStatus(
            Long id,
            String status) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found with id: "
                                                + id
                                )
                        );

        TicketStatus ticketStatus;

        try {

            ticketStatus =
                    TicketStatus.valueOf(
                            status.toUpperCase()
                    );

        } catch (IllegalArgumentException ex) {

            throw new RuntimeException(
                    "Invalid ticket status: " + status
            );
        }

        ticket.setStatus(ticketStatus);

        ticket.setUpdatedAt(
                LocalDateTime.now()
        );

        // Set resolved time
        if (ticketStatus == TicketStatus.RESOLVED) {

            ticket.setResolvedAt(
                    LocalDateTime.now()
            );
        }

        // Set closed time
        if (ticketStatus == TicketStatus.CLOSED) {

            ticket.setClosedAt(
                    LocalDateTime.now()
            );
        }

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(updatedTicket);
    }


    // =========================================================
    // UPDATE PRIORITY
    // =========================================================

    @Override
    public TicketResponse updatePriority(
            Long id,
            Priority priority) {

        Ticket ticket =
                ticketRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ticket not found with id: "
                                                + id
                                )
                        );

        ticket.setPriority(priority);

        ticket.setUpdatedAt(
                LocalDateTime.now()
        );

        Ticket updatedTicket =
                ticketRepository.save(ticket);

        return convertToResponse(updatedTicket);
    }


    // =========================================================
    // GET TICKETS BY PRIORITY
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByPriority(
            Priority priority) {

        return ticketRepository
                .findByPriority(priority)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    // =========================================================
    // GENERATE TICKET NUMBER
    // =========================================================

    private String generateTicketNumber() {

        return "TKT-" +
                System.currentTimeMillis();
    }


    // =========================================================
    // ENTITY → DTO
    // =========================================================

    private TicketResponse convertToResponse(
            Ticket ticket) {

        TicketResponse response =
                new TicketResponse();

        response.setId(
                ticket.getId()
        );

        response.setTicketNumber(
                ticket.getTicketNumber()
        );

        response.setTitle(
                ticket.getTitle()
        );

        response.setDescription(
                ticket.getDescription()
        );

        response.setCategory(
                ticket.getCategory()
        );

        response.setPriority(
                ticket.getPriority()
        );

        response.setStatus(
                ticket.getStatus()
        );

        response.setCreatedAt(
                ticket.getCreatedAt()
        );

        response.setUpdatedAt(
                ticket.getUpdatedAt()
        );

        response.setDueDate(
                ticket.getDueDate()
        );

        response.setResolvedAt(
                ticket.getResolvedAt()
        );

        response.setClosedAt(
                ticket.getClosedAt()
        );

        if (ticket.getCreatedBy() != null) {

            response.setCreatedById(
                    ticket.getCreatedBy().getId()
            );

            response.setCreatedByName(
                    ticket.getCreatedBy().getName()
            );
        }

        if (ticket.getAssignedTo() != null) {

            response.setAssignedToId(
                    ticket.getAssignedTo().getId()
            );

            response.setAssignedToName(
                    ticket.getAssignedTo().getName()
            );
        }

        return response;
    }
}