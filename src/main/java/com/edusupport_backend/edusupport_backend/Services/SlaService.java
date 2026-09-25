package com.edusupport_backend.edusupport_backend.Services;

import com.edusupport_backend.edusupport_backend.Entity.Sla;

import java.util.List;

public interface SlaService {

    Sla createSla(Long ticketId);

    Sla getSlaByTicketId(Long ticketId);

    List<Sla> getAllSlas();

    Sla updateSla(Long ticketId);

    void deleteSla(Long ticketId);

    void checkSlaBreaches();
}