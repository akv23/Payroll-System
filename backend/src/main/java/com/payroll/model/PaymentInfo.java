package com.payroll.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentInfo {

    @Field("bank_name")
    private String bankName;

    @Field("branch_name")
    private String branchName;

    @Field("account_number")
    private String accountNumber;

    @Field("ifsc_code")
    private String ifscCode;

    @Field("account_holder_name")
    private String accountHolderName;
}
