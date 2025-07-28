package com.payroll.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "salary_slips")
public class SalarySlip {
    @Id
    private String id;
    private String empId;
    private int month;
    private int year;
    private double netSalary;
    private LocalDate generatedDate;
    private PaymentInfo paymentInfo;    // Embedded Payment Info (Bank details)
    private List<SalaryComponentBreakdown> breakdown; // // Embedded Salary Component Breakdown

}
