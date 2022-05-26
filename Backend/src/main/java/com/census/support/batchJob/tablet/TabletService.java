package com.census.support.batchJob.tablet;

import com.census.support.helper.response.BaseResponse;
import com.census.support.ticket.Ticket;
import com.census.support.ticket.TicketDTO;
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

import javax.persistence.Table;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;


@Service
public class TabletService {
    @Autowired
    TabletRepository tabletRepository;


    public ResponseEntity<?> searchTabletBySimNo(String simNo) {
        Tablet tablet = tabletRepository.findBySimNo(simNo);
        if (tablet == null) {
            return new ResponseEntity<>(new BaseResponse(false, "Tablet not found", HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(true, "Tablet found", HttpStatus.OK.value(), tablet), HttpStatus.OK);
    }

    public ResponseEntity<?> searchTabletByBarCode(String barCode) {
        try {
            if(barCode.length() < 10 ){
                return new ResponseEntity<>(new BaseResponse(false, "Enter at least 10 digits/characters ", HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
            }
            Tablet tablet = tabletRepository.getByBarCode(barCode);
            if (tablet == null) {
                return new ResponseEntity<>(new BaseResponse(false, "Tablet not found", HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseResponse(true, "Tablet found", HttpStatus.OK.value(), tablet), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, " Something went wrong : "+e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
        }
    }

    public Page<TabletDTO> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<Tablet> entities = tabletRepository.findAll((Specification<Tablet>) (root, cq, cb) -> {

            Predicate p = cb.conjunction();
            if (!clientParams.isEmpty()) {

                if (clientParams.containsKey("barCode")) {
                    if (StringUtils.hasLength(clientParams.get("barCode"))) {
                        p = cb.and(p, cb.like(root.get("barCode"), "%" + clientParams.get("barCode") + "%"));
                    }
                }
                if(clientParams.containsKey("simNo")){
                    if(StringUtils.hasLength(clientParams.get("simNo"))){
                        p = cb.and(p, cb.like(root.get("simNo"), "%" + clientParams.get("simNo") + "%"));
                    }
                }
            }
            return p;
        }, pageable);
        return entities.map(TabletDTO::new);
    }
}
