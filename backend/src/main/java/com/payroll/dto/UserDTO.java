package com.payroll.dto;

import com.payroll.model.Role;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private Role role;

    // Constructor
    public UserDTO(String username, Role role) {
        this.username = username;
        this.role = role;
    }
}
