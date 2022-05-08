package com.census.support.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> , JpaSpecificationExecutor<Ticket> {
    Ticket findByCode(String code);

    Ticket getByCode(String code);

    List<Ticket> findByStatus(String holdSts);

    List<Ticket> findByStatusAndStatusSequence(String holdSts, long l);
}
