package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.OrganizationDto;
import com.edu.ruse.studypal.dtos.OrganizationPostDto;
import com.edu.ruse.studypal.entities.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "topLectorId", source = "organization.topLectorId")
    @Mapping(target = "organizationId", source = "organization.organizationId")
    OrganizationDto toDto(Organization organization);
    Organization toEntity(OrganizationDto organizationDto);

    OrganizationPostDto toPostDto(Organization organization);
    Organization toEntityFromPostDto(OrganizationPostDto organizationPostDto);
}
