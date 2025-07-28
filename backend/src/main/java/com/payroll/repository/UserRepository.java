package com.payroll.repository;


import com.payroll.model.Role;
import com.payroll.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    // Custom query to find users by role
    List<User> findByRoles(Role role);
    boolean existsByUsername(String username);
}