package com.payroll.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PayComponent {

    @Field("name")
    private ListPayComponent componentType;

    @Field("amount")
    private double amount;

    @Field("frequency")
    private Frequency frequency; // e.g., "Monthly", "Biweekly", etc.

    @Field("used")
    private boolean used; // Mark true once used

}

