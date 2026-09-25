package com.edusupport_backend.edusupport_backend.DTO;

import com.edusupport_backend.edusupport_backend.Enums.Priority;
import com.edusupport_backend.edusupport_backend.Enums.TicketCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    private TicketCategory category;

    @NotNull(message = "Priority is required")
    private Priority priority;

    private Long assignedTo;
}