package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.FacultyGetDto;
import com.edu.ruse.studypal.dtos.FacultyPostDto;
import com.edu.ruse.studypal.entities.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface FacultyMapper {
    @Mapping(target = "organizationDto.name", source = "organization.name")
    @Mapping(target = "organizationDto.adminOrg", source = "organization.adminOrg.user_id")
    FacultyGetDto toDto(Faculty faculty);

    Faculty toEntity(FacultyGetDto dto);

    FacultyGetDto toPostDto(Faculty faculty);

    @Mapping(target = "organization.organizationId", source = "organizationId")
    Faculty toEntityFromPostDto(FacultyPostDto facultyPostDto);
}
