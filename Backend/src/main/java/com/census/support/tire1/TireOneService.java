package com.census.support.tire1;

import com.census.support.acl.user.User;
import com.census.support.helper.response.BaseResponse;
import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketDTO;
import com.census.support.ticket.TicketRepository;
import com.census.support.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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

                if (clientParams.containsKey("status")) {
                    if (StringUtils.hasLength(clientParams.get("status"))) {
                        p = cb.and(p, cb.equal(root.get("status"), clientParams.get("status")));
                    }
                }

                if (clientParams.containsKey("receivedFromT1")) {
                    if (StringUtils.hasLength(clientParams.get("receivedFromT1"))) {
                        p = cb.or(p, cb.equal(root.get("receivedFromT1"),  clientParams.get("receivedFromT1")));

                    }
                }




            }
            return p;
        }, pageable);

        return entities.map(TicketDTO::new);
    }

    public ResponseEntity<?> stsUpdate(Long id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setStatus("RECEIVED");
                ticket.setReceivedFromT1(UserUtil.getLoginUser());
                ticket.setReceiveTime(new Date());
                ticketRepository.save(ticket);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket receive successfully", 200), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Optional<Ticket> entity = ticketRepository.findById(id);
            if (entity.isPresent()) {
                TicketDTO dto = new TicketDTO(entity.get());
                return new ResponseEntity<>(new BaseResponse(true, "Ticket found successfully", 200, dto), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Something went wrong: "+e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> holdTicket(TicketDTO entityDTO,Long id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setStatus("HOLD");
                ticket.setHoldBy(UserUtil.getLoginUser());
                ticket.setHoldTime(new Date());
                ticket.setHoldDuration(entityDTO.getHoldDuration());
                ticket.setSolutionType(entityDTO.getSolutionType());
                ticket.setSolutionDescription(entityDTO.getSolutionDescription());
                ticketRepository.save(ticket);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket Hold successfully", 200), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }
}
