package com.payroll.mapper;

import org.mapstruct.Mapper;

import com.payroll.dto.PaymentInfoDTO;
import com.payroll.model.PaymentInfo;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentInfoMapper {

    PaymentInfo toEntity(PaymentInfoDTO paymentInfoDTO);
    PaymentInfoDTO toDTO(PaymentInfo paymentInfo);
}
