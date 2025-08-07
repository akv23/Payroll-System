package com.payroll.model;

import java.time.LocalDate;

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
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payrolls")
public class Payroll {
    @Id
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