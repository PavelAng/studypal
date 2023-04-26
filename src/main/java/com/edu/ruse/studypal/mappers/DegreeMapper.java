package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.DegreeGetDto;
import com.edu.ruse.studypal.dtos.DegreePostDto;
import com.edu.ruse.studypal.entities.Degree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DegreeMapper {
    @Mapping(target = "organizationDto.name", source = "organization.name")
    @Mapping(target = "organizationDto.description", source = "organization.description")
    DegreeGetDto toDto(Degree degree);

    Degree toEntity(DegreeGetDto degreeGetDto);

    DegreePostDto toPostDto(Degree degree);

    @Mapping(target = "organization.organizationId", source = "organizationId")
    Degree toEntityFromPostDto(DegreePostDto degreePostDto);
}
