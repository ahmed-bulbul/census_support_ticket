package com.census.support.tire1;

import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketDTO;
import com.census.support.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Map;

@Service
public class TireOneService {
    @Autowired
    private TicketRepository ticketRepository;

    public Page<TicketDTO> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Ticket> entities = ticketRepository.findAll((Specification<Ticket>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (!clientParams.isEmpty()) {

                if (clientParams.containsKey("code")) {
                    if (StringUtils.hasLength(clientParams.get("code"))) {
                        p = cb.and(p, cb.equal(root.get("code"), clientParams.get("code")));
                    }
                }
                if (clientParams.containsKey("creationUser")) {
                    if (StringUtils.hasLength(clientParams.get("creationUser"))) {
                        p = cb.and(p, cb.equal(root.get("creationUser"), clientParams.get("creationUser")));
                    }
                }
                if (clientParams.containsKey("problemCategory")) {
                    if (StringUtils.hasLength(clientParams.get("problemCategory"))) {
                        p = cb.and(p, cb.equal(root.get("problemCategory"), clientParams.get("problemCategory")));
                    }
                }
            }
            return p;
        }, pageable);

        return entities.map(TicketDTO::new);
    }
}
