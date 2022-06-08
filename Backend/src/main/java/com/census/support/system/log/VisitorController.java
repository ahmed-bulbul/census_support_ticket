package com.census.support.system.log;

import com.census.support.acl.user.UserDto;
import com.census.support.helper.response.PaginatedResponse;
import com.census.support.util.PaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/visitor")
public class VisitorController {

    public Map<String, String> clientParams;
    @Autowired
    private VisitorService service;

    // get list of visitors
    @RequestMapping("/getList")
    public ResponseEntity<PaginatedResponse> getAllPaginatedUsers(HttpServletRequest request,
                                                                  @RequestParam Map<String,String> clientParams) {
        this.clientParams = clientParams;
        PaginatorService ps = new PaginatorService(request);
        Page<Visitor> page = this.service.getAllPaginatedList(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<Visitor> listData = page.getContent();

       /*for(Visitor visitor : listData) {
           System.out.println(visitor.getCreationDateTime() + " \n" + visitor.getUrl() + " \n" + visitor.getId());
           System.out.println("\n");
       }*/

        return new ResponseEntity<>(new PaginatedResponse(true,200,"ok",page.getTotalElements(),
                page.getTotalPages(),ps.sortDir.equals("asc") ? "desc": "asc",page.getNumber(), Arrays.asList(listData.toArray())), HttpStatus.OK);
    }

}
