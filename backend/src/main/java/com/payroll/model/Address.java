package com.payroll.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Field("street")
    private String street;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("postalCode")
    private String postalCode;

    @Field("country")
    private String country;
}

