package com.census.support.acl.security.jwt.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
}
