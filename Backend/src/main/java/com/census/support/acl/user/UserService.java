package com.census.support.acl.user;

import com.census.support.acl.role.Role;
import com.census.support.acl.role.RoleRepository;
import com.census.support.helper.response.BaseResponse;
import com.census.support.util.SetAttributeUpdate;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<?> createUser(User createEntity) {
        Optional<User> local = this.repository.findByUsername(createEntity.getUsername());
        if (local.isPresent()) {
            log.info("User is already exists");
            return new ResponseEntity<>(new BaseResponse(false, "User already exists", 302), HttpStatus.FOUND);
        } else {
            // call setAttributeForCreateUpdate
            SetAttributeUpdate.setSysAttributeForCreateUpdate(createEntity,"Create");
            this.repository.save(createEntity);
            return new ResponseEntity<>(new BaseResponse(true, "User created successfully", 201), HttpStatus.CREATED);
        }
    }

    public Page<UserDto> getAllPaginatedUserDto(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        log.info("getAllPaginatedUserDto()" + clientParams);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<User> entities = repository.findAll((Specification<User>) (root, cq, cb) -> {
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

        return entities.map(entity -> {
            UserDto dto = new UserDto();
            // Conversion logic
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setPhone(entity.getPhone());
            dto.setName(entity.getName());
            dto.setPassword(entity.getPassword());
            dto.setCreationDateTime(entity.getCreationDateTime());
            dto.setLastUpdateDateTime(entity.getLastUpdateDateTime());
            dto.setCreationUser(entity.getCreationUser());
            dto.setLastUpdateUser(entity.getLastUpdateUser());
            dto.setRole(entity.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()));
            return dto;
        });
    }

    public ResponseEntity<?> getById(Long id, Map<String, String> clientParams) {
        log.info("Client params: {}", clientParams);
        try {
            Optional<User> entityInst = this.repository.findById(id);
            if (entityInst.isPresent()) {
                //convert to dto
                UserDto userDto = new UserDto(entityInst.get());
                return new ResponseEntity<>(new BaseResponse(true, "Success", 200, userDto), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new BaseResponse(false, "User not found", 404), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "User not found", 404), HttpStatus.OK);
        }

    }

    public ResponseEntity<?> getAllRoles() {
        try {
            return new ResponseEntity<>(new BaseResponse(true, "Success", 200, this.roleRepository.findAll()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "Role not found", 404), HttpStatus.OK);
        }
    }
}
