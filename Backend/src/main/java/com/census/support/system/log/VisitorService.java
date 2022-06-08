package com.census.support.system.log;


import com.census.support.acl.role.Role;
import com.census.support.acl.user.User;
import com.census.support.acl.user.UserDto;
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
public class VisitorService {

    @Autowired
    private VisitorRepository repository;

    public Visitor saveVisitorInfo(Visitor visitor) {
        return repository.save(visitor);
    }

    public Page<Visitor> getAllPaginatedList(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<Visitor> entities = repository.findAll((Specification<Visitor>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (!clientParams.isEmpty()) {

                if (clientParams.containsKey("username")) {
                    if (StringUtils.hasLength(clientParams.get("username"))) {
                        p = cb.and(p, cb.equal(root.get("username"), clientParams.get("username")));
                    }
                }
            }
            return p;
        }, pageable);

        return entities;
    }
}