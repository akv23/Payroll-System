package com.payroll.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payroll.model.SalarySlip;

public interface SalarySlipRepository extends MongoRepository<SalarySlip, String> {
    List<SalarySlip> findByEmpId(String empId);
    List<SalarySlip> findByEmpIdAndMonthAndYear(String empId, int month, int year);
}
