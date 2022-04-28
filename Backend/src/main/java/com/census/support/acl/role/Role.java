package com.census.support.acl.role;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACL_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "ROLE_SEQ")
    @Column(name = "ID")
    private Long id;
    private String authority;
    private String description;

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

    public Role( String authority, String description, Date creationDateTime, String creationUser) {

        this.authority = authority;
        this.description = description;
        this.creationDateTime=creationDateTime;
        this.creationUser=creationUser;
    }
}
