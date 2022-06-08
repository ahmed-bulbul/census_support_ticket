package com.census.support.acl.config;
import com.census.support.system.log.VisitorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private VisitorLogger visitorLogger;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorLogger);
    }

}