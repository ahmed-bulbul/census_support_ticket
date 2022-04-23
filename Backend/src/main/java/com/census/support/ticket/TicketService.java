package com.census.support.ticket;

import com.census.support.helper.response.BaseResponse;
import com.census.support.message.MessageService;
import com.census.support.system.counter.SystemCounterService;
import com.census.support.ticket.log.TicketLog;
import com.census.support.ticket.log.TicketLogRepository;
import com.census.support.util.SetAttributeUpdate;
import com.census.support.util.SysMessage;
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
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SystemCounterService counterService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TicketLogRepository ticketLogRepository;


    @Transactional
    public ResponseEntity<?> create(Ticket entity) {
        entity.setCode(counterService.getNextFormattedValue("TICKET_CODE_CNT"));
        try {
            Ticket ticket = ticketRepository.getByCode(entity.getCode());
            if (ticket != null) {
                return new ResponseEntity<>(new BaseResponse(false, "Ticket already exists", 302), HttpStatus.OK);
            }else {
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entity,"Create");
                ticketRepository.save(entity);
                //send user ticket created message
                messageService.sendTicketCreatedMessage(entity, SysMessage.OPEN_MSG);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket created successfully", 201), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Ticket creation failed: "+e.getMessage(), 500), HttpStatus.OK);
        }
    }


    public Page<TicketDTO> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize,
                                                    String sortField, String sortDir) {
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
            }
            return p;
        }, pageable);

        return entities.map(TicketDTO::new);
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

    public ResponseEntity<?> update(TicketDTO entityDTO) {
        Optional<Ticket> entity = ticketRepository.findById(entityDTO.getId());
        if (entity.isPresent() && entity.get().getStatus().equals(SysMessage.OPEN_STS)) {
            try {
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entity.get(), "Update");
                Ticket ticket = entity.get();
                ticket.setDeviceUserPhone(entityDTO.getDeviceUserPhone());
                ticket.setTabletSerialNo(entityDTO.getTabletSerialNo());
                ticket.setDeviceUserId(entityDTO.getDeviceUserId());
                ticket.setProblemCategory(entityDTO.getProblemCategory());

                ticketRepository.save(ticket);
                return new ResponseEntity<>(new BaseResponse(true, "Ticket updated successfully", 201), HttpStatus.OK);

            }catch (Exception e){
                return new ResponseEntity<>(new BaseResponse(false, "Ticket update failed: "+e.getMessage(), 302), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new BaseResponse(false, "Something went wrong ", 404), HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        try {
            Optional<Ticket> entity = ticketRepository.findById(id);
            if (entity.isPresent() && entity.get().getStatus().equals(SysMessage.OPEN_STS)) {
                ticketRepository.delete(entity.get());
                return new ResponseEntity<>(new BaseResponse(true, "Ticket deleted successfully", 200), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new BaseResponse(false, "Ticket not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public Page<TicketDTO> getAllPaginatedStatus(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<TicketLog> entities = ticketLogRepository.findAll((Specification<TicketLog>) (root, cq, cb) -> {
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
            }
            return p;
        }, pageable);

        return entities.map(TicketDTO::new);
    }
}
