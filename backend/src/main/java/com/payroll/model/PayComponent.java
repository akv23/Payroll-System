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
    private String name;

    @Field("amount")
    private double amount;

    @Field("frequency")
    private Set<Frequency> frequency; // e.g., "Monthly", "Biweekly", etc.

    @Field("used")
    private boolean used; // Mark true once used

}

