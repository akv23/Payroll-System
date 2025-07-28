package com.payroll.dto;

import java.util.List;


import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CompInfoDTO {

    @Valid
    private List<PayComponentDTO> payComponentDTO;
    @Valid
    private PaymentInfoDTO paymentInfoDTO;
}
