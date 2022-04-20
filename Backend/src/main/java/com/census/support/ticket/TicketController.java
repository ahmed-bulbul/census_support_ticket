package com.census.support.ticket;


import com.census.support.helper.response.PaginatedResponse;
import com.census.support.util.PaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket/bbs")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    public Map<String,String > clientParams;

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody Ticket entity) {
        return ticketService.create(entity);
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<TicketDTO> page = this.ticketService.getAllPaginatedLists(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<TicketDTO> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return ticketService.getById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TicketDTO entityDTO){
        return ticketService.update(entityDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ticketService.delete(id);
    }


}





