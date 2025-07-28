package com.payroll.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.payroll.dto.EmployeeDTO;
import com.payroll.model.Employee;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",uses = {AddressMapper.class},nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "address", source = "addressDTO")
    Employee toEntity(EmployeeDTO employeeDTO);

    @Mapping(target = "addressDTO", source = "address")
    EmployeeDTO toDTO(Employee employee);

} 