package com.census.support.system.menu.dto;

import com.census.support.system.menu.SystemMenu;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SystemMenuDTO {

    private Long id;
    private String code;
    private String description;
    private String openUrl;
    private String subUrl;
    private Integer sequence;
    private String iconHtml;
    private Boolean hasChild;
    private Boolean isActive;
    private Boolean visibleToAll;
    private Boolean leftSideMenu;
    private Boolean isChild;
    private Long organizationId;
    private String organizationName;
    private Long parentMenuId;
    private String parentMenuDescription;
    private String chkAuthorization;

    //system logs
    private Date creationDateTime;
    private Date lastUpdateDateTime;
    private String creationUser;
    private String lastUpdateUser;

    public SystemMenuDTO(SystemMenu systemMenu) {
        this.id = systemMenu.getId();
        this.code = systemMenu.getCode();
        this.description = systemMenu.getDescription();
        this.openUrl = systemMenu.getOpenUrl();
        this.subUrl = systemMenu.getSubUrl();
        this.iconHtml = systemMenu.getIconHtml();
        this.hasChild = systemMenu.getHasChild();
        this.isActive = systemMenu.getIsActive();
        this.sequence = systemMenu.getSequence();
        this.visibleToAll = systemMenu.getVisibleToAll();
        this.leftSideMenu = systemMenu.getLeftSideMenu();
        this.isChild = systemMenu.getIsChild();
        this.parentMenuId = systemMenu.getParentMenu()==null?null:systemMenu.getParentMenu().getId();
        this.parentMenuDescription = systemMenu.getParentMenu()==null?"":systemMenu.getParentMenu().getDescription();
        this.chkAuthorization = systemMenu.getChkAuthorization();
        this.creationDateTime = systemMenu.getCreationDateTime();
        this.lastUpdateDateTime = systemMenu.getLastUpdateDateTime();
        this.creationUser = systemMenu.getCreationUser();
        this.lastUpdateUser = systemMenu.getLastUpdateUser();



    }
}
