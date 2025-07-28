package com.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.mapper.EmployeeDataMapper;
import com.payroll.repository.EmployeeDataRepository;
import com.payroll.model.EmployeeData;
import com.payroll.dto.EmployeeDataRequestDTO;
import com.payroll.dto.EmployeeDataResponseDTO;
import com.payroll.exception.DuplicateResourceException;
import com.payroll.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {
    
    @Autowired
    private EmployeeDataRepository employeeDataRepository;

    @Autowired
    private EmployeeDataMapper employeeDataMapper;
    

    // Create a new employeeData
    public EmployeeDataResponseDTO createEmployeeData(EmployeeDataRequestDTO requestEmployeeDataDTO) {
        if (employeeDataRepository.existsByEmpId(requestEmployeeDataDTO.getEmpId())) {
            throw new DuplicateResourceException("Employee ID already exists");
        }

        EmployeeData employeeData = employeeDataMapper.toEntity(requestEmployeeDataDTO);
        try {
             employeeData = employeeDataRepository.save(employeeData);
        } catch (DuplicateResourceException e) {
            throw new DuplicateResourceException("Employee ID already exists");
        }
        return employeeDataMapper.toResponseDTO(employeeData);
    }

    // Get employeeData by Employee ID
    public EmployeeDataResponseDTO getEmployeeDataByEmpId(String empId) {
        EmployeeData employeeData = employeeDataRepository.findByEmpId(empId)
                .orElseThrow(() -> new NotFoundException("Employee ID not found"));
        return employeeDataMapper.toResponseDTO(employeeData);
    }

    // Get all employeeData
    public List<EmployeeDataResponseDTO> getAllEmployeeData() {
        try{
            List<EmployeeData> employeeDataList = employeeDataRepository.findAll();
            if (employeeDataList.isEmpty()) {
                throw new NotFoundException("No employee data found");
            }
            return employeeDataMapper.toResponseDTOs(employeeDataList);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving employee data: " + e.getMessage());
        }
    }

    // Update employeeData
    public EmployeeDataResponseDTO updateEmployeeData(String empId, EmployeeDataRequestDTO requestEmployeeDataDTO) {
        EmployeeData employeeData = employeeDataRepository.findByEmpId(empId)
                .orElseThrow(() -> new NotFoundException("Employee ID not found"));
        
        employeeDataMapper.updateEntityFromRequest(employeeData, requestEmployeeDataDTO);
        employeeData = employeeDataRepository.save(employeeData);
        return employeeDataMapper.toResponseDTO(employeeData);
    }

    // Delete employeeData
    public void deleteEmployeeData(String empId) {
        EmployeeData employeeData = employeeDataRepository.findByEmpId(empId)
                .orElseThrow(() -> new NotFoundException("Employee ID not found"));
        employeeDataRepository.delete(employeeData);
    }
}
