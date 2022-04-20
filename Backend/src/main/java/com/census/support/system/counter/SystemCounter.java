package com.census.support.system.counter;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SYSTEM_COUNTER")
@Data
public class SystemCounter {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String counterName;
    String code;

    String prefix;
    Long nextNumber;
    String suffix;
    Integer step;
    Integer counterWidth;
    String prefixSeparator;
    String suffixSeparator;
//    String outputSeparator

    Long beginLimit;
    Long finalLimit;
    String numerationType; // for generate Integer/Character

    String usedEntityName;
    Boolean fiscalYearPrefix;
    Boolean active;

    // for more flexible filter counter dropdown use below attributes
    String counterType; // use it for more flexible with dropdown option --- preloaded dropdown ---> like Sales Counter, BoM Counter

    // System log fields
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATION_DATETIME")
    Date creationDateTime;
    @Column(name = "CREATION_USER")
    String creationUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "LAST_UPDATE_DATETIME")
    Date lastUpdateDateTime;
    @Column(name = "LAST_UPDATE_USER")
    String lastUpdateUser;
}
