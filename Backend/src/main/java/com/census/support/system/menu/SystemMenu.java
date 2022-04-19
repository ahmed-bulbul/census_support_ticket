package com.census.support.system.menu;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name="SYSTEM_MENU")
public class SystemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    String code; // menuCode is unique for all, if needed then user will add menuCode+orgCode
    String description;


    String entityName;
    String openUrl;
    String mainUrl;
    String subUrl;
    Integer sequence;
    String iconHtml;
    Boolean hasChild;
    Boolean visibleToAll;
    String chkAuthorization;
    String chkAuthorizationChar;
    Boolean leftSideMenu;
    Boolean dashboardMenu;
    Boolean mainHeaderMenu;

    Boolean isChild;
    Boolean isOpenNewTab;
    Boolean isActive;



    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parent_menu_id", referencedColumnName = "id")
    SystemMenu parentMenu;
    String parentMenuCode;

    Boolean superAdminAccessOnly;
    Boolean adminAccessOnly;




    // System log fields
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATION_DATETIME")
    Date creationDateTime;
    @Column(name = "CREATION_USER")
    String creationUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "LAST_UPDATE_DATETIME")
    Date lastUpdateDateTime;
    @Column(name = "LAST_UPDATE_USER")
    String lastUpdateUser;

    public SystemMenu() {
    }

    public SystemMenu(String code, String description, String openUrl, String subUrl, String iconHtml, Boolean hasChild,
                      Boolean visibleToAll, Boolean leftSideMenu, Boolean isChild, Boolean isActive, SystemMenu parentMenu) {
        this.code = code;
        this.description = description;
        this.openUrl = openUrl;
        this.subUrl = subUrl;
        this.iconHtml = iconHtml;
        this.hasChild = hasChild;
        this.visibleToAll = visibleToAll;
        this.leftSideMenu = leftSideMenu;
        this.isChild = isChild;
        this.isActive = isActive;
        this.parentMenu = parentMenu;
    }
}
