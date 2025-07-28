package com.payroll.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.dto.PayrollDTO;
import com.payroll.mapper.PayrollMapper;
import com.payroll.model.CompInfo;
import com.payroll.model.EmployeeData;
import com.payroll.model.PayComponent;
import com.payroll.model.Payroll;
import com.payroll.model.SalaryComponentBreakdown;
import com.payroll.repository.EmployeeDataRepository;
import com.payroll.repository.PayrollRepository;
import com.payroll.util.PayrollUtil;

@Service
public class PayrollService {

    @Autowired
    private EmployeeDataRepository employeeDataRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private PayrollMapper payrollMapper;

    @Autowired
    private PayrollUtil payrollUtil;

    @Autowired
    private SalarySlipService salarySlipService;

    public PayrollDTO generatePayroll(String empId, int month, int year) {
        EmployeeData employee = employeeDataRepository.findByEmpId(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        CompInfo compInfo = employee.getCompInfo();

        List<PayComponent> components = compInfo.getPayComponent();
        List<SalaryComponentBreakdown> breakdownList = payrollUtil.calculateBreakdown(components);
        double grossSalary = payrollUtil.calculateGrossSalary(breakdownList);
        double tax = payrollUtil.calculateTax(grossSalary);
        double netSalary = grossSalary - tax;

        Payroll payroll = new Payroll(UUID.randomUUID().toString(), empId, month, year,
                grossSalary, 0, 0, tax, grossSalary, netSalary, LocalDate.now());

        payrollRepository.save(payroll);
        employeeDataRepository.save(employee); // Save one-time flags

        salarySlipService.createSalarySlip(empId, month, year, netSalary,
                compInfo.getPaymentInfo(), breakdownList);

        return payrollMapper.toDto(payroll);
    }

    public PayrollDTO getPayrollByEmpId(String empId) {
        List<Payroll> payrollList = payrollRepository.findByEmpId(empId);
        if (payrollList.isEmpty()) {
            throw new RuntimeException("Payroll not found: " + empId);
        }
        return payrollMapper.toDto(payrollList.get(0));
    }
    
    public List<PayrollDTO> getPayrollByEmpIdAndMonthAndYear(String empId, int month, int year) {
        List<Payroll> payrollList = payrollRepository.findByEmpIdAndMonthAndYear(empId, month, year);
        if (payrollList.isEmpty()) {
            throw new RuntimeException("Payroll not found for employee: " + empId + ", month: " + month + ", year: " + year);
        }
        return payrollMapper.toDtoList(payrollList);
    }
}