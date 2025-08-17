package com.payroll.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import com.payroll.model.EmployeeData;

public interface EmployeeDataRepository extends MongoRepository<EmployeeData, String> {
    @NonNull
    Optional<EmployeeData> findById(@NonNull String id);
    Optional<EmployeeData> findByEmpId(String emplId);
    Boolean existsByEmpId(String empId);

    Void deleteByEmpId(String empId);

}
