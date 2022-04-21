package com.census.support.acl.security.jwt.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String name;


    @NotBlank
    private String phone;

    @NotBlank
    @Size(min=6,max=40)
    private String username;


    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    private Set<String> role;



}
