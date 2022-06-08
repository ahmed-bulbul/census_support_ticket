package com.census.support.system.log;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

@Setter
@Getter
@Data
@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String ip;
    private String method;
    private String url;
    private String page;
    private String queryString;
    private String refererPage;
    private String userAgent;
    private LocalDateTime loggedTime;
    private boolean uniqueVisit;

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