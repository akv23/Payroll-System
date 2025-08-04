package com.payroll.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.payroll.model.EmployeeData;

import com.payroll.dto.EmployeeDataRequestDTO;
import com.payroll.dto.EmployeeDataResponseDTO;

@Mapper(componentModel = "spring", uses = {CompInfoMapper.class, EmployeeMapper.class, JobInfoMapper.class, CompanyMapper.class}, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeDataMapper {
    
    // Convert EmployeeData to EmployeeDataResponseDTO
    @Mapping(target = "employeeDTO", source = "employee")
    @Mapping(target = "companyDTO", source = "company")
    @Mapping(target = "jobInfoDTO", source = "jobInfo")
    @Mapping(target = "compInfoDTO", source = "compInfo")
    EmployeeDataResponseDTO toResponseDTO(EmployeeData employeeData);

    // Convert EmployeeDataRequestDTO to EmployeeData
    @Mapping(target = "employee", source = "employeeDTO")
    @Mapping(target = "company", source = "companyDTO")
    @Mapping(target = "jobInfo", source = "jobInfoDTO")
    @Mapping(target = "compInfo", source = "compInfoDTO")
    @Mapping(target = "id", ignore = true)
    EmployeeData toEntity(EmployeeDataRequestDTO employeeDataRequestDTO);

    // List all EmployeeDataResponseDTO
    List<EmployeeDataResponseDTO> toResponseDTOs(List<EmployeeData> employeeDataList);

    // List all EmployeeData
    List<EmployeeData> toEntities(List<EmployeeDataRequestDTO> employeeDataRequestDTOs);


}
