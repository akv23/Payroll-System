package com.payroll.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payroll.model.Payroll;

public interface PayrollRepository extends MongoRepository<Payroll, String> {
    List<Payroll> findByEmpId(String empId);
    List<Payroll> findByEmpIdAndMonthAndYear(String empId, int month, int year);
}
