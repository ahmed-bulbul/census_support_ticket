package com.census.support.system.menu.dto;

import com.census.support.system.menu.SystemMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class MenuNode {

    String code;
    String description;
    String openUrl;
    String iconHtml;
    Boolean hasChild;
    Integer level;
    Map<String, MenuNode> child = new LinkedHashMap<>();

    public MenuNode(){
    }

    public MenuNode(String code, String description, String openUrl, String iconHtml, Boolean hasChild, Integer level) {
        this.code = code;
        this.description = description;
        this.openUrl = openUrl;
        this.iconHtml = iconHtml;
        this.hasChild = hasChild;
        this.level = level;
    }

    public MenuNode(SystemMenu systemMenu, int level, boolean hasChild ) {

        String code = systemMenu.getCode();
        String description = systemMenu.getDescription();
        String openUrl = systemMenu.getOpenUrl();
        String iconHtml = systemMenu.getIconHtml();

        openUrl = (openUrl == null || openUrl.trim().isEmpty()) ? "#" : openUrl;

        this.code = code;
        this.description = description;
        this.openUrl = openUrl;
        this.iconHtml = iconHtml;
        this.hasChild = hasChild;
        this.level = level;

    }


}
