package com.edusupport_backend.edusupport_backend.Services;

import com.edusupport_backend.edusupport_backend.DTO.CommentRequest;
import com.edusupport_backend.edusupport_backend.Entity.TicketComment;

import java.util.List;

public interface CommentService {

    TicketComment addComment(
            Long ticketId,
            Long userId,
            CommentRequest request
    );

    List<TicketComment> getTicketComments(
            Long ticketId
    );

    TicketComment getCommentById(
            Long commentId
    );

    TicketComment updateComment(
            Long commentId,
            CommentRequest request
    );

    void deleteComment(
            Long commentId
    );
}