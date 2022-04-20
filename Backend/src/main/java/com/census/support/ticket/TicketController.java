package com.census.support.ticket;


import com.census.support.util.PaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping("getTicket/{ticketId}")
    public Ticket getTicket(@PathVariable("ticketId") Long ticketId) {
        return ticketService.getTicket(ticketId);
    }

    @GetMapping("/getAll")
    ResponseEntity<Map<String, Object>> getAllPaginatedHrCrEmpLeave(HttpServletRequest request, @RequestParam Map<String,String> clientParams){


        PaginatorService pSrv = new PaginatorService(request);
        Page<Ticket> page = this.ticketService.getAllPaginate(clientParams,pSrv.pageNum, pSrv.pageSize, pSrv.sortField, pSrv.sortDir);
        List<Ticket> listData = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("objectList", listData);
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalItems", page.getTotalElements());
        response.put("reverseSortDir", (pSrv.sortDir.equals("asc") ? "desc" : "asc"));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}





