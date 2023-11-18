package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.OrganizationsController;
import com.edu.ruse.studypal.dtos.OrganizationDto;
import com.edu.ruse.studypal.dtos.OrganizationPostDto;
import com.edu.ruse.studypal.entities.Organization;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.exceptions.NotValidJsonBodyException;
import com.edu.ruse.studypal.mappers.OrganizationMapper;
import com.edu.ruse.studypal.repositories.OrganizationRepository;
import com.edu.ruse.studypal.security.services.UserDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private static final int PAGE_SIZE = 10;
    private static final Logger LOGGER = LogManager.getLogger(OrganizationsController.class);

    private final UserDetailsServiceImpl userService;
    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, UserDetailsServiceImpl userService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
    }

    public OrganizationDto createOrganization(OrganizationPostDto organizationPostDto) {
        userService.getUserById(organizationPostDto.getAdminOrg());

        Organization organization = organizationMapper.toEntityFromPostDto(organizationPostDto);
        Organization res = organizationRepository.save(organization);

        return organizationMapper.toDto(res);
    }

    public List<OrganizationDto> getAllOrganizations(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Organization> all = organizationRepository.findAll(pageable);
        List<OrganizationDto> res = all.getContent().stream().map(organizationMapper::toDto).collect(Collectors.toList());

        return res;
    }

    public OrganizationDto getOrganizationById(long id) {
        Optional<Organization> organization = organizationRepository.findById(id);

        if (organization.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract organization entity with id " + id);
        }
        Organization organizationEntity = organization.get();

        return organizationMapper.toDto(organizationEntity);
    }

    public OrganizationDto updateOrganization(long id, OrganizationPostDto dto) throws IllegalAccessException {
        Optional<Organization> toUpdate = getOrganizationEntity(id);
        Organization entityToUpdate = toUpdate.get();
        String desc = dto.getDescription();
        String name = dto.getName();
        Long lector = dto.getAdminOrg();

        if (desc == null || name == null) {
            LOGGER.info("Request body not valid!");
            throw new NotValidJsonBodyException("Request body not valid!");
        }

        if (!Objects.equals(entityToUpdate.getDescription(), desc)) {
            entityToUpdate.setDescription(desc);
        }
        if (!Objects.equals(entityToUpdate.getName(), name)) {
            entityToUpdate.setName(name);
        }
        if (!Objects.equals(entityToUpdate.getAdminOrg().getUser_id(), lector)) {
            entityToUpdate.setAdminOrg(userService.getUserById(lector));
        }
        organizationRepository.save(entityToUpdate);

        return organizationMapper.toDto(entityToUpdate);
    }

    private Optional<Organization> getOrganizationEntity(long id) {
        Optional<Organization> toUpdate = organizationRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Organization with id {} not found", id);
            throw new EntityNotFoundException("Organization not found");
        }

        return toUpdate;
    }

    public void deleteOrganization(long id) {
        Optional<Organization> toDelete = getOrganizationEntity(id);
        organizationRepository.delete(toDelete.get());
    }
}
