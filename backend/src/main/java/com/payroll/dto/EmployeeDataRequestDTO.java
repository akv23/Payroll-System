package com.payroll.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class EmployeeDataRequestDTO {
    
    @Pattern(regexp = "^EMP\\d{4}$", message = "Employee ID must start with EMP followed by 4 digits")
    @NotNull(message = "Employee ID cannot be null")
    private String empId;
    @Valid
    private EmployeeDTO employeeDTO;
    @Valid
    private CompanyDTO companyDTO;
    @Valid
    private JobInfoDTO jobInfoDTO;
    @Valid
    private CompInfoDTO compInfoDTO;
}
