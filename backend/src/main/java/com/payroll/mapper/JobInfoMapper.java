package com.payroll.mapper;

import org.mapstruct.Mapper;
import com.payroll.dto.JobInfoDTO;
import com.payroll.model.JobInfo;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface JobInfoMapper {
    
    JobInfoDTO toDTO(JobInfo jobInfo);

    JobInfo toEntity(JobInfoDTO jobInfoDTO);
}
