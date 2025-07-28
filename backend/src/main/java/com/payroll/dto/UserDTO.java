package com.payroll.dto;

import java.util.Set;

import com.payroll.model.Role;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private Set<Role> role;

    // Constructor
    public UserDTO(String username, Set<Role> role) {
        this.username = username;
        this.role = role;
    }
}
