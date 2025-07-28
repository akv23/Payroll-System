package com.payroll.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.payroll.dto.SalarySlipDTO;
import com.payroll.model.SalarySlip;

@Mapper(componentModel = "spring", uses = {PaymentInfoMapper.class, SalaryComponentBreakdownMapper.class},nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface SalarySlipMapper {

    SalarySlipMapper INSTANCE = Mappers.getMapper(SalarySlipMapper.class);

    @Mapping(target = "paymentInfoDTO", source = "paymentInfo")
    @Mapping(target = "breakdownDTO", source = "breakdown")
    SalarySlipDTO toDto(SalarySlip salarySlip);

    @Mapping(target = "paymentInfo", source = "paymentInfoDTO")
    @Mapping(target = "breakdown", source = "breakdownDTO")
    SalarySlip toEntity(SalarySlipDTO dto);

    List<SalarySlipDTO> toDtoList(List<SalarySlip> salarySlips);
}