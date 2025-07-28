package com.payroll.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payroll.model.EmployeeData;

public interface EmployeeDataRepository extends MongoRepository<EmployeeData, String> {
    
    Optional<EmployeeData> findById(String id);
    Optional<EmployeeData> findByEmpId(String emplId);
    Boolean existsByEmpId(String empId);

    Void deleteByEmpId(String empId);

}
