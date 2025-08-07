package com.payroll.mapper;

import org.mapstruct.Mapper;

import com.payroll.dto.AddressDTO;
import com.payroll.model.Address;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    // Convert Address to AddressDTO
    AddressDTO toDTO(Address address);

    // Convert AddressDTO to Address
    Address toEntity(AddressDTO addressDTO);
}
