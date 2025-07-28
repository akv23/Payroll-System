package com.payroll.mapper;

import org.mapstruct.Mapper;

import com.payroll.dto.AddressDTO;
import com.payroll.model.Address;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressDTO toDTO(Address address);
    Address toEntity(AddressDTO addressDTO);
}
