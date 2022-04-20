package com.census.support.ticket;

import com.census.support.acl.user.User;
import com.census.support.acl.user.UserRepository;
import com.census.support.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Map;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }


    public Ticket getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public Page<Ticket> getAllPaginate(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);


        Page<Ticket> approvalProcesses = this.ticketRepository.findAll((Specification<Ticket>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();

            return p;



        }, pageable);
        return approvalProcesses;
    }
}
