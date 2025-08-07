package com.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.payroll.model.ListPayComponent;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryComponentBreakdownDTO {
    private ListPayComponent componentType;
    private double amount;
    private String frequency;
}