package com.census.support.dashboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    public Map<String, String> clientParams;


    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/getTotalTickets")
    public ResponseEntity<?> getTotalTickets() {
        return dashboardService.getTotalTickets();
    }

    //get received tickets
    @GetMapping("/getReceivedTickets")
    public ResponseEntity<?> getReceivedTickets() {
        return dashboardService.getReceivedTickets();
    }

    //get hold tickets
    @GetMapping("/getHoldTickets")
    public ResponseEntity<?> getHoldTickets() {
        return dashboardService.getHoldTickets();
    }
    //get resolved tickets
    @GetMapping("/getResolvedTickets")
    public ResponseEntity<?> getResolvedTickets() {
        return dashboardService.getResolvedTickets();
    }

    // get terminated tickets
    @GetMapping("/getTerminatedTickets")
    public ResponseEntity<?> getTerminatedTickets() {
        return dashboardService.getTerminatedTickets();
    }

    //get Send to tier 2 tickets
    @GetMapping("/getSendToTier2Tickets")
    public ResponseEntity<?> getSendToTier2Tickets() {
        return dashboardService.getSendToTier2Tickets();
    }

    //get total Open tickets
    @GetMapping("/getTotalOpenTickets")
    public ResponseEntity<?> getTotalOpenTickets() {
        return dashboardService.getTotalOpenTickets();
    }



}
