package com.census.support.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

//    @GetMapping("/getByTicketCode/{code}")
//    public ResponseEntity<?>  getByTicketCode(@PathVariable("code") String code) {
//        return ResponseEntity.ok(messageService.getByTicketCode(code));
//    }
}
