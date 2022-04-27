package com.census.support.ticket;

import com.census.support.ticket.entityListener.TicketListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private String devicePhone;
    private String deviceUserId;
    private String tabletSerialNo;
    private String problemCategory;
    private String problemType;

    private String problemDescription;
    private String code;

    private String status;
    private Long statusSequence;






    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date holdTime;
    private String holdDuration;
    private String solutionType;
    private String solutionDescription;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date solveTime;
    private String solvedBy;
    private String receivedFromT1;
    private String receivedFromT2;
    private String holdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date receiveTime;
    private String receiveDuration;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date terminateTime;
    private String terminateBy;

    //tire 2 related
    private String tier2ProblemDescription;
    private String tier2SendBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tier2SendTime;
    private String tier2ReceiveBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tier2ReceiveTime;
    private String tier2SolutionType;
    private String tier2SolutionDescription;
    private String tier2SolvedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tier2SolveTime;






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
