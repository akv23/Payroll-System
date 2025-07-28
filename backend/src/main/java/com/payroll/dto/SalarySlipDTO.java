package com.payroll.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalarySlipDTO {
    private String id;
    private String empId;
    private int month;
    private int year;
    private double netSalary;
    private LocalDate generatedDate;
    private PaymentInfoDTO paymentInfoDTO;
    private List<SalaryComponentBreakdownDTO> breakdownDTO;
}

