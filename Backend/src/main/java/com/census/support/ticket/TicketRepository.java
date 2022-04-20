package com.census.support.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketRepository extends JpaRepository<Ticket, Long> , JpaSpecificationExecutor<Ticket> {
    Ticket findByCode(String code);

    Ticket getByCode(String code);
}
