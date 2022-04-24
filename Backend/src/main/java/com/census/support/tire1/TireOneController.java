package com.census.support.tire1;

import com.census.support.helper.response.PaginatedResponse;
import com.census.support.ticket.TicketDTO;
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
@RequestMapping("/ticket/tire1")
public class TireOneController {
    @Autowired
    private TireOneService tireOneService;
    public Map<String,String > clientParams;

    @GetMapping("/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<TicketDTO> page = this.tireOneService.getAllPaginatedLists(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<TicketDTO> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @PutMapping("/stsUpdate/{id}")
    public ResponseEntity<?> stsUpdate(@PathVariable("id") Long id){
        return tireOneService.stsUpdate(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return tireOneService.getById(id);
    }
    @PutMapping("/holdTicket/{id}")
    public ResponseEntity<?> holdTicket(@RequestBody TicketDTO entityDTO,@PathVariable("id") Long id){
        return tireOneService.holdTicket(entityDTO,id);
    }

    @PutMapping("/solveTicket/{id}")
    public ResponseEntity<?> solveTicket(@RequestBody TicketDTO entityDTO,@PathVariable("id") Long id){
        return tireOneService.solveTicket(entityDTO,id);
    }
}
