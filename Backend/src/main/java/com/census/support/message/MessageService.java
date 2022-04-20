package com.census.support.message;

import com.census.support.ticket.Ticket;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public void sendTicketCreatedMessage(Ticket entity) {
        System.out.println("Ticket created: " + entity.getId());

        //call external service to send message via api

    }
}
