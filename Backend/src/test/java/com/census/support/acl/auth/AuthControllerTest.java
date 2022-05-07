package com.census.support.acl.auth;

import com.census.support.acl.security.jwt.config.JwtUtils;
import com.census.support.acl.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;


    @Test
    @DisplayName("Login Controller Test")
    void userLogin() {
        assertTrue(true);
    }
}