package com.edusupport_backend.edusupport_backend.Repository;


import com.edusupport_backend.edusupport_backend.Entity.TicketActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository
        extends JpaRepository<TicketActivity, Long> {

    List<TicketActivity> findByTicketIdOrderByCreatedAtDesc(Long ticketId);
}