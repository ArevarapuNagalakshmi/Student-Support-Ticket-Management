package com.edusupport_backend.edusupport_backend.Services;

import com.edusupport_backend.edusupport_backend.DTO.TicketRequest;
import com.edusupport_backend.edusupport_backend.DTO.TicketResponse;
import com.edusupport_backend.edusupport_backend.Enums.Priority;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(
            TicketRequest request,
            Long userId
    );

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(Long id);

    TicketResponse updateTicket(
            Long id,
            TicketRequest request
    );

    void deleteTicket(Long id);

    TicketResponse updateStatus(
            Long id,
            String status
    );

    TicketResponse updatePriority(
            Long id,
            Priority priority
    );

    List<TicketResponse> getTicketsByPriority(
            Priority priority
    );
}