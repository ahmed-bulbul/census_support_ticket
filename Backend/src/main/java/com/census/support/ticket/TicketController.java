package com.census.support.ticket;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
public class TicketController {

    @GetMapping("/bbs/list")
    public String getTicketListForBBS() {
        // create dummy json response
        return "{\"data\":[{\"id\":\"1\",\"title\":\"Ticket 1\",\"description\":\"Ticket 1 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"},{\"id\":\"2\",\"title\":\"Ticket 2\",\"description\":\"Ticket 2 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"},{\"id\":\"3\",\"title\":\"Ticket 3\",\"description\":\"Ticket 3 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"}]}";
    }
    @GetMapping("/tire1/list")
    public String getTicketListForTire1() {
        // create dummy json response
        return "{\"data\":[{\"id\":\"1\",\"title\":\"Ticket 1\",\"description\":\"Ticket 1 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"},{\"id\":\"2\",\"title\":\"Ticket 2\",\"description\":\"Ticket 2 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"},{\"id\":\"3\",\"title\":\"Ticket 3\",\"description\":\"Ticket 3 description\",\"status\":\"Open\",\"created_at\":\"2020-01-01 00:00:00\",\"updated_at\":\"2020-01-01 00:00:00\"}]}";
    }

}
