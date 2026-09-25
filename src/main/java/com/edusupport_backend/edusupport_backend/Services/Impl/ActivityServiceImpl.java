package com.edusupport_backend.edusupport_backend.Services.Impl;


import com.edusupport_backend.edusupport_backend.Entity.Ticket;
import com.edusupport_backend.edusupport_backend.Entity.TicketActivity;
import com.edusupport_backend.edusupport_backend.Entity.User;
import com.edusupport_backend.edusupport_backend.Repository.ActivityRepository;
import com.edusupport_backend.edusupport_backend.Repository.TicketRepository;
import com.edusupport_backend.edusupport_backend.Repository.UserRepository;
import com.edusupport_backend.edusupport_backend.Services.ActivityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public ActivityServiceImpl(
            ActivityRepository activityRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository) {

        this.activityRepository = activityRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TicketActivity createActivity(
            Long ticketId,
            Long userId,
            String action,
            String description) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        TicketActivity activity = TicketActivity.builder()
                .ticket(ticket)
                .user(user)
                .action(action)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        return activityRepository.save(activity);
    }

    @Override
    public List<TicketActivity> getTicketActivities(
            Long ticketId) {

        return activityRepository
                .findByTicketIdOrderByCreatedAtDesc(ticketId);
    }
}