package com.census.support.message;

import com.census.support.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByTicketCode(String code);

    List<Message> findByTicket(Optional<Ticket> ticket);
}
