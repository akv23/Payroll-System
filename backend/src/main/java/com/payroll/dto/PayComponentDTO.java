package com.payroll.dto;


import java.util.Set;

import com.payroll.model.Frequency;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayComponentDTO {
    
    @NotBlank(message = "Component ID cannot be blank")
    private String name;

    @Min(0)
    private double amount;

    @NotEmpty(message = "Frequency set cannot be empty")
    private Set<Frequency> frequency;   // e.g., "Monthly", "Biweekly", etc.

    private boolean used;
}
