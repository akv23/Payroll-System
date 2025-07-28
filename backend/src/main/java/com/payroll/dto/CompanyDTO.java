package com.payroll.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
public class CompanyDTO {
    
    @NotNull(message = "Company name cannot be null")
    private String companyName;
    @Valid
    private AddressDTO addressDTO;
    private String contactEmail;
    private String contactPhone;

}
