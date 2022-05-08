package com.census.support.tier2;


import com.census.support.helper.response.BaseResponse;
import com.census.support.message.MessageService;
import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketDTO;
import com.census.support.ticket.TicketRepository;
import com.census.support.util.SysMessage;
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
public class TierTwoService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MessageService messageService;


    public Page<TicketDTO> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Ticket> entities = ticketRepository.findAll((Specification<Ticket>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();

            if (clientParams.containsKey("status")) {
                if (StringUtils.hasLength(clientParams.get("status"))) {
                    p = cb.and(p, cb.equal(root.get("status"), clientParams.get("status")));
                }
            }
            // p=cb.or(p,cb.equal(root.get("status"),SysMessage.RECEIVED_T2_STS));

            if (clientParams.containsKey("tier2ReceiveBy")) {
                if (StringUtils.hasLength(clientParams.get("tier2ReceiveBy"))) {
                    p = cb.or(p, cb.equal(root.get("tier2ReceiveBy"), clientParams.get("tier2ReceiveBy")));

                }
            }
            p = cb.and(p, cb.notEqual(root.get("status"), SysMessage.RESOLVED_T2_STS));
            p = cb.and(p, cb.notEqual(root.get("status"), SysMessage.TERMINATE_T2_STS));

            return p;
        }, pageable);

        return entities.map(TicketDTO::new);
    }

    public ResponseEntity<?> stsUpdate(Long id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setStatus(SysMessage.RECEIVED_T2_STS);
                ticket.setTier2ReceiveBy(UserUtil.getLoginUser());
                ticket.setTier2ReceiveTime(new Date());
                ticketRepository.save(ticket);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket receive successfully", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }

    }

    public ResponseEntity<?> solveTicket(TicketDTO entityDTO, Long id) {

        try {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setStatus(SysMessage.RESOLVED_T2_STS);
                ticket.setTier2SolvedBy(UserUtil.getLoginUser());
                ticket.setTier2SolveTime(new Date());
                ticket.setTier2SolutionType(entityDTO.getTier2SolutionType());
                ticket.setTier2SolutionDescription(entityDTO.getTier2SolutionDescription());
                ticketRepository.save(ticket);
                //send user ticket created message
                messageService.sendTicketCreatedMessage(ticket, SysMessage.SOLVED_MSG);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket solved successfully", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> terminateTicket(Long id) {
        try {
            Ticket ticket = ticketRepository.findById(id).orElse(null);
            if (ticket != null) {
                ticket.setStatus(SysMessage.TERMINATE_T2_STS);
                ticket.setTier2TerminateBy(UserUtil.getLoginUser()); //terminate by
                ticket.setTier2SendTime(new Date());
                ticketRepository.save(ticket);
                //send user ticket terminate message
                messageService.sendTicketCreatedMessage(ticket, SysMessage.TERMINATE_MSG);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket terminated successfully", 200), HttpStatus.OK);


            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Optional<Ticket> entity = ticketRepository.findById(id);
            if (entity.isPresent()) {
                TicketDTO dto = new TicketDTO(entity.get());
                return new ResponseEntity<>(new BaseResponse(true, "Ticket found successfully", 200, dto), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Something went wrong: " + e.getMessage(), 500), HttpStatus.OK);
        }

    }
}