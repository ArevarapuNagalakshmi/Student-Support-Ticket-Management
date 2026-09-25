package com.edusupport_backend.edusupport_backend.Services.Impl;

import com.edusupport_backend.edusupport_backend.DTO.CommentRequest;
import com.edusupport_backend.edusupport_backend.Entity.Ticket;
import com.edusupport_backend.edusupport_backend.Entity.TicketComment;
import com.edusupport_backend.edusupport_backend.Entity.User;
import com.edusupport_backend.edusupport_backend.Repository.CommentRepository;
import com.edusupport_backend.edusupport_backend.Repository.TicketRepository;
import com.edusupport_backend.edusupport_backend.Repository.UserRepository;
import com.edusupport_backend.edusupport_backend.Services.CommentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository) {

        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // ADD COMMENT
    // =========================================================

    @Override
    public TicketComment addComment(
            Long ticketId,
            Long userId,
            CommentRequest request) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found with id: " + ticketId
                        )
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: " + userId
                        )
                );

        TicketComment comment = TicketComment.builder()
                .ticket(ticket)
                .user(user)
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment);
    }

    // =========================================================
    // GET COMMENTS BY TICKET
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<TicketComment> getTicketComments(
            Long ticketId) {

        if (!ticketRepository.existsById(ticketId)) {

            throw new RuntimeException(
                    "Ticket not found with id: " + ticketId
            );
        }

        return commentRepository
                .findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    // =========================================================
    // GET COMMENT BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public TicketComment getCommentById(
            Long commentId) {

        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Comment not found with id: "
                                        + commentId
                        )
                );
    }

    // =========================================================
    // UPDATE COMMENT
    // =========================================================

    @Override
    public TicketComment updateComment(
            Long commentId,
            CommentRequest request) {

        TicketComment comment =
                commentRepository.findById(commentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Comment not found with id: "
                                                + commentId
                                )
                        );

        comment.setComment(
                request.getComment()
        );

        comment.setUpdatedAt(
                LocalDateTime.now()
        );

        return commentRepository.save(comment);
    }

    // =========================================================
    // DELETE COMMENT
    // =========================================================

    @Override
    public void deleteComment(
            Long commentId) {

        TicketComment comment =
                commentRepository.findById(commentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Comment not found with id: "
                                                + commentId
                                )
                        );

        commentRepository.delete(comment);
    }
}