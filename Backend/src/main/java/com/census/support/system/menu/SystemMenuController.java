package com.census.support.system.menu;


import com.census.support.helper.response.PaginatedResponse;
import com.census.support.system.menu.dto.SystemMenuDTO;
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
@RequestMapping("/system/systemMenu")
@CrossOrigin("*")
public class SystemMenuController {

    @Autowired
    private SystemMenuService systemMenuService;
    public Map<String,String > clientParams;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SystemMenu entity){
        return systemMenuService.create(entity);
    }

    @GetMapping("/getLists")
    public ResponseEntity<?> getAllPaginatedResponse(HttpServletRequest request,
                                                     @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<SystemMenuDTO> page = this.systemMenuService.getAllPaginatedLists(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<SystemMenuDTO> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        return systemMenuService.getById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SystemMenuDTO entityDTO){
        return systemMenuService.update(entityDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return systemMenuService.delete(id);
    }

    @GetMapping("/getMenuData")
    public ResponseEntity<?> getAppMenuData(){
        return this.systemMenuService.getMenuData();
    }

    @GetMapping("/getParentMenu")
    public ResponseEntity<?> getParentMenu(){
        return this.systemMenuService.getParentMenu();
    }


}
