package com.payroll.service;


import com.payroll.exception.AdminNotFoundException;
import com.payroll.exception.OperationNotAllowedException;
import com.payroll.model.Role;
import com.payroll.model.User;
import com.payroll.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    UserRepository userRepository;

    // Get all admins
    public List<User> getAllAdmins() {
        List<User> admins = userRepository.findAll().stream()
                .filter(user -> user.getRoles() != null && user.getRoles()==Role.ADMIN)
                .collect(Collectors.toList());
        if (admins.isEmpty()) {
            throw new AdminNotFoundException("No admins found");
        }
        return admins;
    }

    // Get all super admins
    public List<User> getAllSuperAdmins() {
        List<User> superAdmins = userRepository.findAll().stream()
                .filter(user -> user.getRoles() != null && user.getRoles()==Role.SUPER_ADMIN)
                .collect(Collectors.toList());

        if (superAdmins.isEmpty()) {
            throw new AdminNotFoundException("No super admins found");
        }
        return superAdmins;
    }

    //Update admin role
    public void updateAdminRole(String username, Role newRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin with username '" + username + "' not found"));
        if (user.getRoles()==Role.SUPER_ADMIN) {
            throw new OperationNotAllowedException("Cannot change role of super admin");
        }
        user.setRoles(newRole);
        userRepository.save(user);
    }

    // Delete an admin
    public void deleteAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AdminNotFoundException("Admin with username '" + username + "' not found"));
        if(user.getRoles()==Role.SUPER_ADMIN) {
            throw new OperationNotAllowedException("Cannot delete super admin");
        }
        userRepository.delete(user);
    }
}

