package com.payroll.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDTO {
    private String id;
    private String empId;
    private int month;
    private int year;
    private double baseSalary;
    private double hra;
    private double da;
    private double other;
    private double bonus;
    private double pf;
    private double tax;
    private double grossSalary;
    private double netSalary;
    private LocalDate generatedDate;
}
