package com.payroll.controller;

import com.payroll.dto.LoginRequestDTO;
import com.payroll.dto.RegisterRequestDTO;
import com.payroll.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    // Endpoint for user registration (admin)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequest) {
        authService.registerAdmin(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok("Admin registered successfully");
    }

}
