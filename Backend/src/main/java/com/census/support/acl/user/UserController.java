package com.census.support.acl.user;


import com.census.support.helper.response.PaginatedResponse;
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
@RequestMapping("/acl/user")
@CrossOrigin("*")
public class UserController {

    public Map<String, String> clientParams;

    @Autowired
    private UserService service;

    @GetMapping("/getUserList")
    public ResponseEntity<PaginatedResponse> getAllPaginatedUsers(HttpServletRequest request,
                                                                  @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<UserDto> page = this.service.getAllPaginatedUserDto(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<UserDto> listData = page.getContent();

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

    /**
     * @param id Long
     * @return instance
     */
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id,@RequestParam Map<String,String> clientParams) {
        return this.service.getById(id,clientParams);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        return this.service.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
        return this.service.delete(id);
    }


}
