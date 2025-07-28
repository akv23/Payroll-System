package com.payroll.controller;

import com.payroll.dto.UserDTO;
import com.payroll.model.Role;
import com.payroll.model.User;
import com.payroll.service.AdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    AdminService adminService;

    // Get all admins
    @GetMapping("/all/admins")
    public ResponseEntity<List<UserDTO>> getAllAdmins() {
        System.out.println("Fetching all admins");
        List<User> users = adminService.getAllAdmins();
        List<UserDTO> userDTOs = users.stream()
        .map(user -> new UserDTO(user.getUsername(),user.getRoles()))
        .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    // Get all super admins
    @GetMapping("/all/super")
    public ResponseEntity<List<UserDTO>> getAllSuperAdmins() {
        System.out.println("Fetching all super admins");
        List<User> users = adminService.getAllSuperAdmins();
        List<UserDTO> userDTOs = users.stream()
        .map(user -> new UserDTO(user.getUsername(),user.getRoles()))
        .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    // Update admin role
    @PutMapping("/update")
    public ResponseEntity<String> updateAdminRole(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        Set<Role> newRole = userDTO.getRole(); 
        System.out.println("Attempting to update admin role for username: " + username + " to role: " + newRole);
        adminService.updateAdminRole(username, newRole);
        return ResponseEntity.ok("Admin role updated successfully");
    }
    
    // Delete an user
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String username) {
        System.out.println("Attempting to delete admin with username: " + username);
        adminService.deleteAdmin(username);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}
