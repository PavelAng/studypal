package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.OrganizationDto;
import com.edu.ruse.studypal.dtos.OrganizationPostDto;
import com.edu.ruse.studypal.entities.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "adminOrg", source = "organization.adminOrg.user_id")
    @Mapping(target = "organizationId", source = "organization.organizationId")
    OrganizationDto toDto(Organization organization);

    @Mapping(target = "adminOrg.user_id", source = "adminOrg")
    Organization toEntity(OrganizationDto organizationDto);

    @Mapping(target = "adminOrg", source = "organization.adminOrg.user_id")
    OrganizationPostDto toPostDto(Organization organization);

    @Mapping(target = "adminOrg.user_id", source = "adminOrg")
    Organization toEntityFromPostDto(OrganizationPostDto organizationPostDto);
}
