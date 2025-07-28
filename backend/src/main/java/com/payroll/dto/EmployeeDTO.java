package com.payroll.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO {
   
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "First Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name should contain only letters")
    @Size(min = 2, max = 30, message = "First Name should be between 2 and 30 characters")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name should contain only letters")
    @Size(min = 2, max = 30, message = "Last Name should be between 2 and 30 characters")
    private String lastName;

    @NotNull(message = "Mobile Number cannot be null")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number should be 10 digits")
    private String mobileNumber;

    @NotNull(message = "National ID cannot be null")
    @Pattern(regexp = "^\\d{12}$", message = "National ID should be 12 digits")
    private String nationalId;

    @Valid
    private AddressDTO addressDTO;
}
