package com.payroll.dto;


import java.util.Set;

import com.payroll.model.Frequency;
import com.payroll.model.ListPayComponent;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayComponentDTO {
    
    @NotNull(message = "Component Name cannot be blank")
    private ListPayComponent componentType;

    @Min(0)
    private double amount;

    @NotNull(message = "Frequency set cannot be empty")
    private Frequency frequency;   // e.g., "Monthly", "Biweekly", etc.

    private boolean used;
}
