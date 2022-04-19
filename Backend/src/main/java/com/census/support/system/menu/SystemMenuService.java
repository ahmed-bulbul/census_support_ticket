package com.census.support.system.menu;


import com.census.support.helper.response.BaseResponse;
import com.census.support.system.menu.dto.SystemMenuDTO;
import com.census.support.util.SetAttributeUpdate;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SystemMenuService {

    @Autowired
    private SystemMenuRepository systemMenuRepository;




    public ResponseEntity<?> create(SystemMenu entity) {
        try {
            SystemMenu systemMenu = systemMenuRepository.getByCode(entity.getCode());
            if (systemMenu != null) {
                return new ResponseEntity<>(new BaseResponse(false, "System Menu already exists", 302), HttpStatus.OK);
            } else {
                SetAttributeUpdate.setSysAttributeForCreateUpdate(entity,"Create");
                systemMenuRepository.save(entity);
                return new ResponseEntity<>(new BaseResponse(true, "System Menu created successfully", 201), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new BaseResponse(false, "System Menu creation failed: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public Page<SystemMenuDTO> getAllPaginatedLists(Map<String, String> clientParams, int pageNum, int pageSize,
                                                    String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<SystemMenu> entities = systemMenuRepository.findAll((Specification<SystemMenu>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if (!clientParams.isEmpty()) {

                if (clientParams.containsKey("code")) {
                    if (StringUtils.hasLength(clientParams.get("code"))) {
                        p = cb.and(p, cb.equal(root.get("code"), clientParams.get("code")));
                    }
                }
            }
            return p;
        }, pageable);

        return entities.map(SystemMenuDTO::new);
    }

    public ResponseEntity<?> getById(Long id) {
        try {
            Optional<SystemMenu> entity = systemMenuRepository.findById(id);
            if (entity.isPresent()) {
                SystemMenuDTO menuDTO = new SystemMenuDTO(entity.get());
                return new ResponseEntity<>(new BaseResponse(true, "System Menu found successfully", 200, menuDTO), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new BaseResponse(false, "System Menu not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "System Menu not found: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }
    public ResponseEntity<?> update(SystemMenuDTO entity) {
        Optional<SystemMenu> systemMenu = systemMenuRepository.findById(entity.getId());
        Optional<SystemMenu> parentMenu = Optional.empty();

        if(entity.getLeftSideMenu()!=null){
            if (entity.getParentMenuId() != null) {
                parentMenu = systemMenuRepository.findById(entity.getParentMenuId());
            }
        }
        if (systemMenu.isPresent()) {
            try {
                SetAttributeUpdate.setSysAttributeForCreateUpdate(systemMenu.get(), "Update");
                SystemMenu dbEntityInst = systemMenu.get();
                dbEntityInst.setId(entity.getId());
                dbEntityInst.setCode(entity.getCode());
                dbEntityInst.setCode(entity.getCode());
                dbEntityInst.setDescription(entity.getDescription());
                dbEntityInst.setOpenUrl(entity.getOpenUrl());
                dbEntityInst.setIconHtml(entity.getIconHtml());
                dbEntityInst.setHasChild(entity.getHasChild());
                dbEntityInst.setSequence(entity.getSequence());
                dbEntityInst.setIsActive(entity.getIsActive());
                dbEntityInst.setVisibleToAll(entity.getVisibleToAll());
                dbEntityInst.setIsChild(entity.getIsChild());
                dbEntityInst.setLeftSideMenu(entity.getLeftSideMenu());
                dbEntityInst.setParentMenu(parentMenu.orElse(null));
                systemMenuRepository.save(dbEntityInst);
                return new ResponseEntity<>(new BaseResponse(true, "System Menu updated successfully", 200), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(new BaseResponse(false, "System Menu update failed: " + e.getMessage(), 500), HttpStatus.OK);
            }
        }else {
            return new ResponseEntity<>(new BaseResponse(false, "System Menu not found", 404), HttpStatus.OK);
        }

    }


    public ResponseEntity<?> delete(Long id) {
        try {
            Optional<SystemMenu> entity = systemMenuRepository.findById(id);
            if (entity.isPresent()) {
                systemMenuRepository.delete(entity.get());
                return new ResponseEntity<>(new BaseResponse(true, "System Menu deleted successfully", 200), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new BaseResponse(false, "System Menu not found", 404), HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Error: " + e.getMessage(), 500), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> getMenuData() {
        Map<String, Object> menu = MenuJsonService.getMenuData("calling for menu");
        return new ResponseEntity<>(new BaseResponse(true, "Success", 200, menu), HttpStatus.OK);

    }

    public ResponseEntity<?> getParentMenu() {
        try {
            List<SystemMenu> menus = this.systemMenuRepository.getAllByIsActiveOrderBySequenceAsc(true);
            if (menus.size() > 0) {
                return new ResponseEntity<>(new BaseResponse(true, "Success", 200, menus), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new BaseResponse(false, "Menu not found", 404), HttpStatus.OK);
            }

        }catch (Exception e){
            return new ResponseEntity<>(new BaseResponse(false, "Error : "+e.getMessage(), 500), HttpStatus.OK);
        }
    }


}
