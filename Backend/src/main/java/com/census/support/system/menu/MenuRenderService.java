package com.census.support.system.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class MenuRenderService {

    static String leftSideMenuSession = "appLeftSideMenu";
    static String clientBaseUrl="http://localhost:4200";

    
    private static ServletContext context;
    private static HttpSession httpSession;
    private static SystemMenuRepository repository;
    private static SystemMenuAuthService systemMenuAuthService;


    @Autowired
    MenuRenderService(SystemMenuRepository repository, HttpSession httpSession, SystemMenuAuthService systemMenuAuthService, ServletContext context){
        this.repository = repository;
        this.httpSession = httpSession;
        this.systemMenuAuthService = systemMenuAuthService;
        this.context = context;
    }
    public static String generateHtmlMenuStringPre(SystemMenu systemMenu,Integer level, Boolean hasChild){

        String code = systemMenu.getCode();
        String description = systemMenu.getDescription();
        String openUrl = systemMenu.getOpenUrl();
        String iconHtml = systemMenu.getIconHtml();
        String mainUrl = systemMenu.getMainUrl();

        openUrl = (openUrl == null || openUrl.trim().isEmpty()) ? "#" : openUrl;
        iconHtml = (iconHtml == null || iconHtml.trim().isEmpty()) ? "<i class=\"far fa-dot-circle nav-icon\"></i>" : iconHtml;

        String menuStr = "" +
                "<li class=\"menu-title\">\n" +
                "            <span>Menus</span>\n" +
                "          </li>"+
                "<ul>" +
                "<li class=\"submenu\" [ngClass]=\"{ 'active' : urlComplete.mainUrl === '" + mainUrl+ "' }\"  mCode=\"" + code +"\" >\n" +
                "   <a href=\"javascript:\" [ngClass]=\"{ 'subdrop' : urlComplete.mainUrl === '" + mainUrl+ "' }\"  class=\"\">\n" +
                "       <i class='" + iconHtml+ "'></i>\n" +
                "       <span>\n" +
                "           "+description+"\n" +
                "       </span>\n" +
                "       <span class=\"menu-arrow\">\n" +
                "       </span>\n" +
                "   </a>\n" +
                "   <ul style=\"display: none;\" [ngStyle]=\"{ 'display' : urlComplete.mainUrl === '"+mainUrl+"' ? 'block' : 'none' }\" class=\"\">";
        return menuStr;
    }
    public static String generateHtmlMenuStringPost(SystemMenu systemMenu,Integer level, Boolean hasChild){
        String menuStr = "" +
                "   </ul>\n" +
                "</li>\n" +
                "</ul>";
        if(level == 0) menuStr += "<li> <hr> </li>";
        return menuStr;
    }

    public static String generateHtmlMenuString(SystemMenu systemMenu, Integer level, Boolean hasChild, boolean lastItem){

        String code = systemMenu.getCode();
        String description = systemMenu.getDescription();
        String openUrl = systemMenu.getOpenUrl();
        String mainUrl = systemMenu.getMainUrl();
        String subUrl = systemMenu.getSubUrl();
        String iconHtml = systemMenu.getIconHtml();

        openUrl = (openUrl == null || openUrl.trim().isEmpty()) ? "#" : openUrl;
        iconHtml = (iconHtml == null || iconHtml.trim().isEmpty()) ? "<i class=\"far fa-dot-circle nav-icon\"></i>" : iconHtml;
        if(openUrl.contains("RootMenu#")) openUrl = "#";

        String contextPath ="";
        String menuStr = "" +
                "<li routerLink=\""+openUrl +"\" class=\"\" mCode=\""+ code +"\" >\n" +
                "   <a [ngClass]=\"{ 'active' : urlComplete.subUrl === '"+subUrl+ "' }\" href=\"javascript:\" class=\"\">\n" +
                "       "+description+"\n" +
                "   </a>\n" +
                "</li>" +
                "";

        if(level == 0 && !lastItem) menuStr += "<li> <hr> </li>";

        return menuStr;
    }
    public static StringBuilder getChildMenuLeftSide(SystemMenu parentMenu, Integer level, StringBuilder menuString){

        List<SystemMenu> systemMenuList;
        if(parentMenu == null){
            systemMenuList = repository.getAllByIsActiveAndParentMenuIsNullOrderBySequenceAsc(true);
        } else {
            systemMenuList = repository.getAllByIsActiveAndParentMenuOrderBySequenceAsc(true, parentMenu);
        }

        int itemSize = systemMenuList.size();
        int itemCount = 0;
        boolean lastItem = false;

        for (SystemMenu systemMenu  : systemMenuList){
            itemCount++;
            if(itemCount == itemSize) lastItem = true;
            String menuCode = systemMenu.getCode();
            String menuUrl = systemMenu.getOpenUrl();

            // check authorization
            if(!systemMenuAuthService.isAuthorized(menuCode, menuUrl)) continue;
            Integer countChild = countChild(systemMenu);
            if (countChild > 0) {
                menuString.append( generateHtmlMenuStringPre(systemMenu, level, true) );
                getChildMenuLeftSide(systemMenu, level + 1, menuString);
                menuString.append( generateHtmlMenuStringPost(systemMenu, level, true) );

            } else {
                menuString.append( generateHtmlMenuString(systemMenu, level, false, lastItem) );
            }

        }
        return menuString;
    }

    public static Integer countChild(SystemMenu menuInst) {
        return repository.countByParentMenuAndIsActive(menuInst, true);
    }





    public static StringBuilder printLeftSideMenu(String caller){
        StringBuilder menu;

        Object hasExist = httpSession.getAttribute(leftSideMenuSession);
        if(hasExist != null){
            menu = (StringBuilder)hasExist;
        } else {
            StringBuilder menuString = new StringBuilder();
            menu = getChildMenuLeftSide(null, 0, menuString);
            // menu = new MenuRenderService().getChildMenuLeftSide(null, 0, menuString);
            httpSession.setAttribute(leftSideMenuSession, menu);
        }

        System.out.println("menu : " + menu.toString());

        return menu;
    }




}
