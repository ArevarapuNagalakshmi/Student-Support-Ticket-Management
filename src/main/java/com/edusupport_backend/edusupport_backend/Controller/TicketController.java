package com.edusupport_backend.edusupport_backend.Controller;

import com.edusupport_backend.edusupport_backend.DTO.TicketRequest;
import com.edusupport_backend.edusupport_backend.DTO.TicketResponse;
import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Services.TicketService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // =========================================================
    // CREATE TICKET
    // =========================================================

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody TicketRequest request,
            @RequestParam Long userId) {

        TicketResponse response =
                ticketService.createTicket(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // =========================================================
    // GET ALL TICKETS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {

        return ResponseEntity.ok(
                ticketService.getAllTickets()
        );
    }

    // =========================================================
    // GET TICKET BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ticketService.getTicketById(id)
        );
    }

    // =========================================================
    // UPDATE TICKET
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequest request) {

        return ResponseEntity.ok(
                ticketService.updateTicket(id, request)
        );
    }

    // =========================================================
    // DELETE TICKET
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                "Ticket deleted successfully"
        );
    }

    // =========================================================
    // UPDATE TICKET STATUS
    // =========================================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                ticketService.updateStatus(id, status)
        );
    }

    // =========================================================
    // UPDATE TICKET PRIORITY
    // =========================================================

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TicketResponse> updatePriority(
            @PathVariable Long id,
            @RequestParam Priority priority) {

        return ResponseEntity.ok(
                ticketService.updatePriority(id, priority)
        );
    }

    // =========================================================
    // GET TICKETS BY PRIORITY
    // =========================================================

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketResponse>> getTicketsByPriority(
            @PathVariable Priority priority) {

        return ResponseEntity.ok(
                ticketService.getTicketsByPriority(priority)
        );
    }
}