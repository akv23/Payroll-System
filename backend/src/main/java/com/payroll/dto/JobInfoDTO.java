package com.payroll.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

import com.payroll.model.EmployeeStatus;
import com.payroll.model.JobLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Data
@Builder
public class JobInfoDTO {

    @NotEmpty(message = "Job Level must contain at least one value")
    private Set<JobLevel> jobLevel;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotNull(message = "Employee status cannot be null")
    private EmployeeStatus employeeStatus;
}
