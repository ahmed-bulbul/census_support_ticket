package com.census.support.tier2;

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
@RequestMapping("/ticket/tire2")
public class TierTwoController {

    @Autowired
    private TierTwoService tierTwoService;

    public Map<String,String > clientParams;

    @GetMapping("/getList")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<TicketDTO> page = tierTwoService.getAllPaginatedLists(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<TicketDTO> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @PutMapping("/stsUpdate/{id}")
    public ResponseEntity<?> stsUpdate(@PathVariable("id") Long id){
        return tierTwoService.stsUpdate(id);
    }
    @PutMapping("/solveTicket/{id}")
    public ResponseEntity<?> solveTicket(@RequestBody TicketDTO entityDTO,@PathVariable("id") Long id){
        return tierTwoService.solveTicket(entityDTO,id);
    }

    @PutMapping("/terminateTicket/{id}")
    public ResponseEntity<?> terminateTicket(@PathVariable("id") Long id){
        return tierTwoService.terminateTicket(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return tierTwoService.getById(id);
    }





}
