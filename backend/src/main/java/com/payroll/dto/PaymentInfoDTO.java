package com.payroll.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class PaymentInfoDTO {
    @NotBlank(message = "Bank name cannot be blank")
    private String bankName;

    @NotBlank(message = "Branch name cannot be blank")
    private String branchName;
    
    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;

    @NotBlank(message = "IFSC code cannot be blank")
    private String ifscCode;

    @NotBlank(message = "Account holder name cannot be blank")
    private String accountHolderName;
}
