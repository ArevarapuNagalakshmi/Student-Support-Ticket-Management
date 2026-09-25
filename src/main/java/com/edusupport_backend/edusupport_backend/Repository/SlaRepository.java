package com.edusupport_backend.edusupport_backend.Repository;

import com.edusupport_backend.edusupport_backend.Entity.Sla;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlaRepository
        extends JpaRepository<Sla, Long> {

    Optional<Sla> findByTicketId(Long ticketId);

    long countByBreachedTrue();
}