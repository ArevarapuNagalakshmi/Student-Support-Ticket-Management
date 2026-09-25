package com.edusupport_backend.edusupport_backend.Repository;

import com.edusupport_backend.edusupport_backend.Entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<TicketComment, Long> {

    List<TicketComment> findByTicketIdOrderByCreatedAtAsc(
            Long ticketId
    );
}