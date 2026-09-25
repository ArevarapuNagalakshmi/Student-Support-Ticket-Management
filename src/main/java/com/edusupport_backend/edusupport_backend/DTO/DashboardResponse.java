package com.edusupport_backend.edusupport_backend.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalTickets;

    private Long openTickets;

    private Long assignedTickets;

    private Long inProgressTickets;

    private Long pendingTickets;

    private Long resolvedTickets;

    private Long closedTickets;

    private Long overdueTickets;

    private Long criticalTickets;
}
