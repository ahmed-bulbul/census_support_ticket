package com.census.support.ticket.log;


import com.census.support.acl.user.User;
import com.census.support.ticket.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TICKET_LOG")
public class TicketLog {

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
    private String solutionType;
    private String solutionDescription;
    private String solvedBy;
    private String receivedFromT1;
    private String receivedFromT2;
    private String holdBy;
    private String holdDuration;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date terminateTime;
    private String terminateBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date solveTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date receiveTime;




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

    public TicketLog(Ticket entity, String action) {
        this.deviceUserPhone = entity.getDeviceUserPhone();
        this.deviceUserId = entity.getDeviceUserId();
        this.devicePhone = entity.getDevicePhone();
        this.tabletSerialNo = entity.getTabletSerialNo();
        this.problemCategory = entity.getProblemCategory();
        this.problemType = entity.getProblemType();
        this.problemDescription = entity.getProblemDescription();
        this.code = entity.getCode();
        this.status = entity.getStatus();
        this.holdTime = entity.getHoldTime();
        this.solutionType = entity.getSolutionType();
        this.solutionDescription = entity.getSolutionDescription();
        this.solvedBy = entity.getSolvedBy();
        this.receivedFromT1 = entity.getReceivedFromT1();
        this.receivedFromT2 = entity.getReceivedFromT2();
        this.holdBy = entity.getHoldBy();
        this.holdDuration = entity.getHoldDuration();
        this.receiveTime = entity.getReceiveTime();
        this.solveTime = entity.getSolveTime();
        this.terminateTime = entity.getTerminateTime();
        this.terminateBy = entity.getTerminateBy();

        this.tier2ProblemDescription = entity.getTier2ProblemDescription();
        this.tier2SendBy = entity.getTier2SendBy();
        this.tier2SendTime = entity.getTier2SendTime();
        this.tier2ReceiveBy = entity.getTier2ReceiveBy();
        this.tier2ReceiveTime = entity.getTier2ReceiveTime();
        this.tier2SolutionType = entity.getTier2SolutionType();
        this.tier2SolutionDescription = entity.getTier2SolutionDescription();
        this.tier2SolvedBy = entity.getTier2SolvedBy();
        this.tier2SolveTime = entity.getTier2SolveTime();



        this.creationDateTime = entity.getCreationDateTime();
        this.creationUser = entity.getCreationUser();
        this.lastUpdateDateTime = entity.getLastUpdateDateTime();
        this.lastUpdateUser = entity.getLastUpdateUser();
    }
}
