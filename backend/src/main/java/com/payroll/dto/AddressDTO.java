package com.payroll.dto;


import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
@Builder
public class AddressDTO {

    private String street;
    private String city;
    private String state;
    
    @Pattern(regexp = "\\d{6}", message = "Postal code must be 6 digits")
    private String postalCode;
    private String country;
}
