package com.payroll.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryComponentBreakdown {
    private ListPayComponent componentType;
    private double amount;
    private String frequency;
}
