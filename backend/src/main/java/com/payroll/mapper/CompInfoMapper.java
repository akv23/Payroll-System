package com.payroll.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.payroll.dto.CompInfoDTO;
import com.payroll.model.CompInfo;

@Mapper(componentModel = "spring", uses = {PayComponentMapper.class, PaymentInfoMapper.class}, nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
public interface CompInfoMapper {
    
    @Mapping(target = "payComponentDTO", source = "payComponent")
    @Mapping(target = "paymentInfoDTO", source = "paymentInfo")
    CompInfoDTO toDTO(CompInfo compInfo);

    @Mapping(target = "payComponent", source = "payComponentDTO")
    @Mapping(target = "paymentInfo", source = "paymentInfoDTO")
    CompInfo toEntity(CompInfoDTO compInfoDTO);
}
