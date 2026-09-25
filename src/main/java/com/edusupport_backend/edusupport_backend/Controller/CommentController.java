package com.edusupport_backend.edusupport_backend.Controller;

import com.edusupport_backend.edusupport_backend.DTO.CommentRequest;
import com.edusupport_backend.edusupport_backend.Entity.TicketComment;
import com.edusupport_backend.edusupport_backend.Services.CommentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(
            CommentService commentService) {

        this.commentService = commentService;
    }

    // =========================================================
    // ADD COMMENT TO TICKET
    // =========================================================

    @PostMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketComment> addComment(
            @PathVariable Long ticketId,
            @RequestParam Long userId,
            @Valid @RequestBody CommentRequest request) {

        TicketComment comment =
                commentService.addComment(
                        ticketId,
                        userId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(comment);
    }

    // =========================================================
    // GET ALL COMMENTS FOR A TICKET
    // =========================================================

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TicketComment>> getTicketComments(
            @PathVariable Long ticketId) {

        List<TicketComment> comments =
                commentService.getTicketComments(ticketId);

        return ResponseEntity.ok(comments);
    }

    // =========================================================
    // GET COMMENT BY ID
    // =========================================================

    @GetMapping("/{commentId}")
    public ResponseEntity<TicketComment> getCommentById(
            @PathVariable Long commentId) {

        TicketComment comment =
                commentService.getCommentById(commentId);

        return ResponseEntity.ok(comment);
    }

    // =========================================================
    // UPDATE COMMENT
    // =========================================================

    @PutMapping("/{commentId}")
    public ResponseEntity<TicketComment> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request) {

        TicketComment comment =
                commentService.updateComment(
                        commentId,
                        request
                );

        return ResponseEntity.ok(comment);
    }

    // =========================================================
    // DELETE COMMENT
    // =========================================================

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }
}