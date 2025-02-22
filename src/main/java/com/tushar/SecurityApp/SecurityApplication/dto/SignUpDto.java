package com.tushar.SecurityApp.SecurityApplication.dto;

import com.tushar.SecurityApp.SecurityApplication.entities.enums.Permission;
import com.tushar.SecurityApp.SecurityApplication.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
//    private Set<Permission> permissions;

}
