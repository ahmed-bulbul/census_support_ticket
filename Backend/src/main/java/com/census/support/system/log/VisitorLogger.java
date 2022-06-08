package com.census.support.system.log;

import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.census.support.util.HttpRequestResponseUtils;
import com.census.support.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class VisitorLogger implements HandlerInterceptor {

    @Autowired
    private VisitorService visitorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        final String ip = HttpRequestResponseUtils.getClientIpAddress();
        final String url = HttpRequestResponseUtils.getRequestUrl();
        final String page = HttpRequestResponseUtils.getRequestUri();
        final String refererPage = HttpRequestResponseUtils.getRefererPage();
        final String queryString = HttpRequestResponseUtils.getPageQueryString();
        final String userAgent = HttpRequestResponseUtils.getUserAgent();
        final String requestMethod = HttpRequestResponseUtils.getRequestMethod();
        final LocalDateTime timestamp = LocalDateTime.now();

        if(page.equals("/census_api/visitor/getList") || page.equals("/census_api/system/systemMenu/getMenuData")){
            return true;
        }

        Visitor visitor = new Visitor();
        visitor.setUsername(HttpRequestResponseUtils.getLoggedInUser());
        visitor.setIp(ip);
        visitor.setMethod(requestMethod);
        visitor.setUrl(url);
        visitor.setPage(page);
        visitor.setQueryString(queryString);
        visitor.setRefererPage(refererPage);
        visitor.setUserAgent(userAgent);
        visitor.setLoggedTime(timestamp);
        visitor.setUniqueVisit(true);

        visitor.setCreationDateTime(new Date());
        visitor.setCreationUser(UserUtil.getLoginUser());

        visitorService.saveVisitorInfo(visitor);

        return true;
    }

}
