package com.census.support.message;

import com.census.support.ticket.Ticket;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public void sendTicketCreatedMessage(Ticket entity,String message) {
        System.out.println(message +" : "+ entity.getCode());

        //call external service to send message via api

    }
}
