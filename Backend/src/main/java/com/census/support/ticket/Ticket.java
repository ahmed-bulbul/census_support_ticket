package com.census.support.ticket;

import com.census.support.acl.user.User;
import com.census.support.ticket.entityListener.TicketListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TICKET")
@EntityListeners(TicketListener.class)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deviceUserPhone;
    private String deviceUserId;
    private String tabletSerialNo;
    private String problemCategory;
    private String problemType;
    private String problemDescription;
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    private User createdBy;
    private String createdByUsername;
    private String status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date holdTime;
    private String solutionType;
    private String solutionDescription;
    @ManyToOne(fetch = FetchType.EAGER)
    private User solvedBy;
    private String solvedByUsername;

    private String receivedFromT1;
    private String receivedFromT2;
    private String holdBy;



    // System log fields
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATION_DATETIME")
    private Date creationDateTime;
    @Column(name = "CREATION_USER")
    private String creationUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "LAST_UPDATE_DATETIME")
    private Date lastUpdateDateTime;
    @Column(name = "LAST_UPDATE_USER")
    private String lastUpdateUser;


}
