package com.payroll.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Company {
   
    @Field("companyName")
    private String companyName;
    private Address address;
    @Field("ContactEmail")
    private String contactEmail;
    @Field("ContactPhone")
    private String contactPhone;
}
