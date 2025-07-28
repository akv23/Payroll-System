package com.payroll.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.payroll.dto.SalaryComponentBreakdownDTO;
import com.payroll.model.SalaryComponentBreakdown;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface SalaryComponentBreakdownMapper {
    SalaryComponentBreakdownDTO toDto(SalaryComponentBreakdown breakdown);
    SalaryComponentBreakdown toEntity(SalaryComponentBreakdownDTO dto);
    List<SalaryComponentBreakdownDTO> toDtoList(List<SalaryComponentBreakdown> list);
}