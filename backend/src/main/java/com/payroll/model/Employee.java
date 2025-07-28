package com.payroll.model;

import lombok.*;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Indexed(unique = true)     // Unique index for email, handle duplicate key exception in the service layer
    @Field("email")
    private String email;

    @Field("phone_number")
    private String mobileNumber;

    @Field("nationalId")
    @Indexed(unique = true)         // Unique index for national ID, handle duplicate key exception in the service layer
    private String nationalId;    

    private Address address;       // Embedded Address
    
}

