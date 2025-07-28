package com.payroll.mapper;

import org.mapstruct.Mapper;

import com.payroll.dto.PayComponentDTO;
import com.payroll.model.PayComponent;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface PayComponentMapper {

    PayComponentDTO toDTO(PayComponent payComponent);
    PayComponent toEntity(PayComponentDTO payComponentDTO);
    
}