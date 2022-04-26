package com.census.support.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Lob;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;

    @Lob
    private String body;
    private String sender;
    private String receiver;
    private String status;
    private String ticketCode;
    private Long ticketId;



    //system logs
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date creationDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date lastUpdateDateTime;
    private String creationUser;
    private String lastUpdateUser;

    public MessageDTO(Message entity) {
        this.id = entity.getId();
        this.body = entity.getBody();
        this.sender = entity.getSender();
        this.receiver = entity.getReceiver();
        this.status = entity.getStatus();
        this.ticketCode = entity.getTicketCode();
        this.ticketId = entity.getTicket().getId();
        this.creationDateTime = entity.getCreationDateTime();
        this.lastUpdateDateTime = entity.getLastUpdateDateTime();
        this.creationUser = entity.getCreationUser();

        //system logs
        this.creationDateTime = entity.getCreationDateTime();
        this.lastUpdateDateTime = entity.getLastUpdateDateTime();
        this.creationUser = entity.getCreationUser();
        this.lastUpdateUser = entity.getLastUpdateUser();
    }
}
