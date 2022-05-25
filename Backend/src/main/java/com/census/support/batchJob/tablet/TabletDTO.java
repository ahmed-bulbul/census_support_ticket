package com.census.support.batchJob.tablet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabletDTO {
    private Long id;

    private String barCode;
    private String imei1;
    private String imei2;
    private String simNo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deliveryDate;

    //system logs
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date creationDateTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date lastUpdateDateTime;
    private String creationUser;
    private String lastUpdateUser;

    public TabletDTO(Tablet tablet) {
        this.id = tablet.getId();
        this.barCode = tablet.getBarCode();
        this.imei1 = tablet.getImei1();
        this.imei2 = tablet.getImei2();
        this.simNo = tablet.getSimNo();
        this.deliveryDate = tablet.getDeliveryDate();
        this.creationDateTime = tablet.getCreationDateTime();
        this.creationUser = tablet.getCreationUser();
        this.lastUpdateDateTime = tablet.getLastUpdateDateTime();
        this.lastUpdateUser = tablet.getLastUpdateUser();
    }
}
