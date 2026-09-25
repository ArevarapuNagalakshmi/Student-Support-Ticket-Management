package com.edusupport_backend.edusupport_backend.Services;



import com.edusupport_backend.edusupport_backend.Entity.TicketActivity;

import java.util.List;

public interface ActivityService {

    TicketActivity createActivity(
            Long ticketId,
            Long userId,
            String action,
            String description
    );

    List<TicketActivity> getTicketActivities(
            Long ticketId
    );
}