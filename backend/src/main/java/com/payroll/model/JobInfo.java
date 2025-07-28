package com.payroll.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobInfo {

    
    @Field("job_level")
    private Set<JobLevel> jobLevel;     // Enum for job levels (e.g., INTERN, ENTRY, MID, SENIOR, etc.)
    
    @Field("job_title")
    private String jobTitle;            // Job title of the employee   
    
    @Field("EmployeeStatus")
    private EmployeeStatus employeeStatus;  // Enum for employee status (e.g., ACTIVE, INACTIVE)
}

