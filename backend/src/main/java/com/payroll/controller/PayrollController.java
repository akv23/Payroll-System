package com.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payroll.dto.PayrollDTO;
import com.payroll.service.PayrollService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // Endpoint to generate payroll for an employee
    @PostMapping("/generate")
    public ResponseEntity<PayrollDTO> generatePayroll(
            @RequestParam String empId,
            @RequestParam int month,
            @RequestParam int year) {
        PayrollDTO payroll = payrollService.generatePayroll(empId, month, year);
        return ResponseEntity.status(HttpStatus.CREATED).body(payroll);
    }

    // Endpoint to get payroll by Employee ID
    @GetMapping("/{empId}")
    public ResponseEntity<PayrollDTO> getPayrollByEmpId(@PathVariable String empId) {
        return ResponseEntity.ok(payrollService.getPayrollByEmpId(empId));
    }

    
    // Endpoint to get payroll by Employee ID and month/year
    @GetMapping("/employee/{empId}/month")
    public ResponseEntity<List<PayrollDTO>> getPayrollByEmpIdMonthYear(
            @PathVariable String empId,
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(payrollService.getPayrollByEmpIdAndMonthAndYear(empId, month, year));
    }
}
