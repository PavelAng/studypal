package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.DegreesController;
import com.edu.ruse.studypal.dtos.DegreeGetDto;
import com.edu.ruse.studypal.dtos.DegreePostDto;
import com.edu.ruse.studypal.entities.Degree;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.exceptions.NotValidJsonBodyException;
import com.edu.ruse.studypal.mappers.DegreeMapper;
import com.edu.ruse.studypal.repositories.DegreeRepository;
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
public class DegreeService {
    private final DegreeRepository degreeRepository;
    private final DegreeMapper degreeMapper;
    private final OrganizationService organizationService;
    private final FacultyService facultyService;
    private static final int PAGE_SIZE = 10;
    private static final Logger LOGGER = LogManager.getLogger(DegreesController.class);

    @Autowired
    public DegreeService(DegreeRepository degreeRepository, DegreeMapper degreeMapper, OrganizationService organizationService, FacultyService facultyService) {
        this.degreeRepository = degreeRepository;
        this.degreeMapper = degreeMapper;
        this.organizationService = organizationService;
        this.facultyService = facultyService;
    }

    public DegreeGetDto createDegree(DegreePostDto degreePostDto) {
        Degree degree = degreeMapper.toEntityFromPostDto(degreePostDto);

        organizationService.getOrganizationById(degreePostDto.getFacultyId());
        Degree savedEntity = degreeRepository.save(degree);
        DegreeGetDto res = degreeMapper.toDto(savedEntity);
        res.setFacultyGetDto(facultyService.getFacultyById(degreePostDto.getFacultyId()));
        LOGGER.info("Created Degree with id = " + res.getDegreeId());

        return res;
    }

    public List<DegreeGetDto> getAllDegrees(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Degree> all = degreeRepository.findAll(pageable);
        List<DegreeGetDto> res = all.getContent().stream().map(degreeMapper::toDto).collect(Collectors.toList());

        return res;
    }


    public DegreeGetDto getDegreeById(long id) {
        Optional<Degree> degreeOptional = degreeRepository.findById(id);

        if (degreeOptional.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract degree entity with id " + id);
        }
        Degree degree = degreeOptional.get();

        return degreeMapper.toDto(degree);
    }

    public DegreeGetDto updateDegree(long id, DegreePostDto degreePostDto) {
        Optional<Degree> toUpdate = getDegreeEntity(id);
        Degree entityToUpdate = toUpdate.get();
        String desc = degreePostDto.getDescription();
        String name = degreePostDto.getName();


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
        degreeRepository.save(entityToUpdate);

        return degreeMapper.toDto(entityToUpdate);
    }

    public void deleteDegree(long id) {
        Optional<Degree> toDelete = getDegreeEntity(id);
        degreeRepository.delete(toDelete.get());
    }

    private Optional<Degree> getDegreeEntity(long id) {
        Optional<Degree> toUpdate = degreeRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Degree with id {} not found", id);
            throw new EntityNotFoundException("Degree not found");
        }

        return toUpdate;
    }
}
