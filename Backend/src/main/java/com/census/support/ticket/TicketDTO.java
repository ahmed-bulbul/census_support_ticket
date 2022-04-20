package com.census.support.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long id;
    private String deviceUserPhone;
    private String deviceUserId;
    private String tabletSerialNo;
    private String problemCategory;
    private String problemType;
    private String problemDescription;
    private String code;
    private Long createById;
    private String createByUsername;
    private String status;
    private Date holdTime;
    private String solutionType;
    private String solutionDescription;
    private Long solvedById;
    private String solvedByUsername;


    //system logs
    private Date creationDateTime;
    private Date lastUpdateDateTime;
    private String creationUser;
    private String lastUpdateUser;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.deviceUserPhone = ticket.getDeviceUserPhone();
        this.deviceUserId = ticket.getDeviceUserId();
        this.tabletSerialNo = ticket.getTabletSerialNo();
        this.problemCategory = ticket.getProblemCategory();
        this.problemType = ticket.getProblemType();
        this.problemDescription = ticket.getProblemDescription();
        this.code = ticket.getCode();
        this.createById = ticket.getCreatedBy()!=null?ticket.getCreatedBy().getId():null;
        this.createByUsername = ticket.getCreatedBy()!=null?ticket.getCreatedBy().getUsername():null;
        this.status = ticket.getStatus();
        this.holdTime = ticket.getHoldTime();
        this.solutionType = ticket.getSolutionType();
        this.solutionDescription = ticket.getSolutionDescription();
        this.solvedById = ticket.getSolvedBy()!=null?ticket.getSolvedBy().getId():null;
        this.solvedByUsername = ticket.getSolvedBy()!=null?ticket.getSolvedBy().getUsername():null;

        //system logs
        this.creationDateTime = ticket.getCreationDateTime();
        this.lastUpdateDateTime = ticket.getLastUpdateDateTime();
        this.creationUser = ticket.getCreationUser();
        this.lastUpdateUser = ticket.getLastUpdateUser();
    }
}