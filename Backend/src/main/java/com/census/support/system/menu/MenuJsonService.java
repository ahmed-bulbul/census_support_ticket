package com.census.support.system.menu;


import com.census.support.system.menu.dto.MenuNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
@SuppressWarnings("unchecked")
public class MenuJsonService {


    static String menuDataSession = "appLeftSideMenu";


    private static HttpSession httpSession;
    private static SystemMenuRepository repository;
    private static SystemMenuAuthService systemMenuAuthService;


    @Autowired
    MenuJsonService(SystemMenuRepository repository, HttpSession httpSession, SystemMenuAuthService systemMenuAuthService){
        this.repository = repository;
        this.httpSession = httpSession;
        this.systemMenuAuthService = systemMenuAuthService;
    }



    public static Map<String, Object> getChildMenu( SystemMenu parentMenu, Integer level, Map<String, Object> menuMap, MenuNode parentMenuNode ){

        List<SystemMenu> systemMenuList;
        if(parentMenu == null){
            systemMenuList = repository.getAllByIsActiveAndParentMenuIsNullOrderBySequenceAsc(true);
        } else {
            systemMenuList = repository.getAllByIsActiveAndParentMenuOrderBySequenceAsc(true, parentMenu);
        }

        for (SystemMenu systemMenu  : systemMenuList){
            log.info("systemMenu : {}", systemMenu.getCode());
            String menuCode = systemMenu.getCode();
            String menuUrl = systemMenu.getOpenUrl();

            // check authorization
            if(!systemMenuAuthService.isAuthorized(menuCode, menuUrl)) continue;
            Integer countChild = countChild(systemMenu);
            if (countChild > 0) {

                MenuNode menuNode = new MenuNode(systemMenu, level, true);

                if(parentMenuNode != null){
                    parentMenuNode.getChild().put( systemMenu.getCode(), menuNode );
                    getChildMenu(systemMenu, level + 1, menuMap, menuNode);
                } else {

                    menuMap.put( systemMenu.getCode(), menuNode );
                    getChildMenu(systemMenu, level + 1, menuMap, menuNode);
                }

            } else {

                MenuNode menuNode = new MenuNode(systemMenu, level, false);
                if(parentMenuNode != null){
                    parentMenuNode.getChild().put(systemMenu.getCode(), menuNode);
                } else {
                    menuMap.put(systemMenu.getCode(), menuNode);
                }


            }

        }
        return menuMap;
    }

    public static Integer countChild(SystemMenu menuInst) {
        return repository.countByParentMenuAndIsActive(menuInst, true);
    }


    public static Map<String, Object> getMenuData(String caller){
        Map<String, Object> menu;

        Object hasExist = httpSession.getAttribute(menuDataSession);
        if(hasExist != null){
            menu = (LinkedHashMap<String, Object>) hasExist;
        } else {
            menu = new LinkedHashMap<>();
            menu = getChildMenu(null, 0, menu, null);
            httpSession.setAttribute(menuDataSession, menu);
        }

        return menu;

    }




}
