package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.OrganizationDto;
import com.edu.ruse.studypal.dtos.OrganizationPostDto;
import com.edu.ruse.studypal.entities.Organization;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationDto toDto(Organization organization);
    Organization toEntity(OrganizationDto organizationDto);

    OrganizationPostDto toPostDto(Organization organization);
    Organization toEntityFromPostDto(OrganizationPostDto organizationPostDto);
}
