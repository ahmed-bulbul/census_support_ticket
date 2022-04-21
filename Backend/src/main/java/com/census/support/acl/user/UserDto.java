package com.census.support.acl.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private Set<String> role;


    private Date creationDateTime;
    private Date lastUpdateDateTime;
    private String creationUser;
    private String lastUpdateUser;


    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();


        this.role = user.getRoles().stream().map(role1 -> {
            Long id = role1.getId();
            String name = role1.getAuthority();
            return id + "-" + name;

        }).collect(Collectors.toSet());
        this.creationDateTime = user.getCreationDateTime();
        this.lastUpdateDateTime = user.getLastUpdateDateTime();
        this.creationUser = user.getCreationUser();
        this.lastUpdateUser = user.getLastUpdateUser();
    }
}