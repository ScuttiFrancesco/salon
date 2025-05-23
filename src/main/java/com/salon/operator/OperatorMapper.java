package com.salon.operator;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperatorMapper {
    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    OperatorDto toDto(Operator operator);

    @Mapping(target = "appointments", ignore = true)
    Operator toEntity(OperatorDto operatorDto);
}
