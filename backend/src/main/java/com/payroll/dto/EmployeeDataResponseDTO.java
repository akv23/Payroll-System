package com.payroll.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDataResponseDTO {
    private String empId;
    private EmployeeDTO employeeDTO;
    private CompanyDTO companyDTO;
    private JobInfoDTO jobInfoDTO;
    private CompInfoDTO compInfoDTO;
}
