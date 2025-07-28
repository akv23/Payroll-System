package com.payroll.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.payroll.dto.PayrollDTO;
import com.payroll.model.Payroll;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface PayrollMapper {
    PayrollDTO toDto(Payroll payroll);
    Payroll toEntity(PayrollDTO dto);
    List<PayrollDTO> toDtoList(List<Payroll> payrolls);
}