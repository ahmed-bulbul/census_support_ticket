package com.census.support.acl.security;

import com.census.support.acl.security.jwt.config.AuthEntryPointJwt;
import com.census.support.acl.security.jwt.config.AuthTokenFilter;
import com.census.support.acl.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/acl/login","/acl/register","/acl/roles","/ticket/bbs/getStatus","/dashboard","/message")
                .permitAll()
                .antMatchers("/api/test/**")
                .permitAll()
                .antMatchers("/tabletInfo/**").hasAnyAuthority("ROLE_SUPER_ADMIN","ROLE_BBS_USER","ROLE_TIRE1_USER","ROLE_TIRE2_USER")
                .antMatchers("/ticket/bbs/**","/searchTablet/**","/searchTabletByBarCode" ).hasAnyAuthority("ROLE_BBS_USER","ROLE_SUPER_ADMIN")
                .antMatchers("/ticket/tire1/**").hasAnyAuthority("ROLE_TIRE1_USER","ROLE_SUPER_ADMIN")
                .antMatchers("/ticket/tire2/**").hasAnyAuthority("ROLE_TIRE2_USER","ROLE_SUPER_ADMIN")
                .antMatchers("/acl/user/**","/jobs/**").hasAnyAuthority("ROLE_SUPER_ADMIN")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}