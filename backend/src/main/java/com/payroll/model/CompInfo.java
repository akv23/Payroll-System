package com.payroll.model;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompInfo {

    private List<PayComponent> payComponent;  // List of Pay Components (e.g., Basic, HRA, etc.)
    private PaymentInfo paymentInfo;    // Embedded Payment Info (Bank details)

}
