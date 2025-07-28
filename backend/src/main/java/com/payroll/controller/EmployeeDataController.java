package com.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payroll.dto.EmployeeDataRequestDTO;
import com.payroll.service.EmployeeDataService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/employee-data")
@RequiredArgsConstructor
public class EmployeeDataController {
    @Autowired
    private EmployeeDataService employeeDataService;

    // Create a new employeeData
    @PostMapping
    public ResponseEntity<?> createEmployeeData(@Valid @RequestBody EmployeeDataRequestDTO employeeDataRequest) {
        return ResponseEntity.ok(employeeDataService.createEmployeeData(employeeDataRequest));
    }
    
    // Get employeeData by Employee ID
    @GetMapping("/{empId}")
    public ResponseEntity<?> getEmployeeData(@PathVariable  String empId) {
        return ResponseEntity.ok(employeeDataService.getEmployeeDataByEmpId(empId));
    }

    // Get all employeeData
    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployeeData() {
        return ResponseEntity.ok(employeeDataService.getAllEmployeeData());
    }
    
    // Update employeeData by Employee ID
    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployeeData(@PathVariable  String empId, @Valid @RequestBody EmployeeDataRequestDTO employeeDataRequest) {
        return ResponseEntity.ok(employeeDataService.updateEmployeeData(empId, employeeDataRequest));
    }

    // Delete employeeData by Employee ID
    @DeleteMapping("/{empId}")
    public ResponseEntity<?> deleteEmployeeData(@RequestParam String empId) {
        employeeDataService.deleteEmployeeData(empId);
        return ResponseEntity.ok("Employee data with ID " + empId + " is deleted successfully");
    }
}
