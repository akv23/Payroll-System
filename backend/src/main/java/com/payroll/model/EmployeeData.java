package com.payroll.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "employeeData")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeData {
    @Id
    private String id;

    @Indexed(unique = true)     // Unique index for employee ID, handle duplicate key exception in the service layer
    private String empId;

    private Employee employee;
    private Company company;
    private JobInfo jobInfo;
    private CompInfo compInfo;

}
