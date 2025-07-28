package com.payroll.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.payroll.dto.CompanyDTO;
import com.payroll.model.Company;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {
    
    @Mapping(target = "addressDTO", source = "address")
    CompanyDTO toDTO(Company company);
    @Mapping(target = "address", source = "addressDTO")
    Company toEntity(CompanyDTO companyDTO);
}
