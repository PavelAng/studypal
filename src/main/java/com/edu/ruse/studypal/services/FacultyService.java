package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.FacultiesController;
import com.edu.ruse.studypal.dtos.FacultyGetDto;
import com.edu.ruse.studypal.dtos.FacultyPostDto;
import com.edu.ruse.studypal.entities.Faculty;
import com.edu.ruse.studypal.entities.RoleEnum;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.exceptions.NotValidJsonBodyException;
import com.edu.ruse.studypal.exceptions.UserNotAppropriateException;
import com.edu.ruse.studypal.mappers.FacultyMapper;
import com.edu.ruse.studypal.repositories.FacultyRepository;
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
public class FacultyService {
    private final FacultyMapper facultyMapper;
    private final FacultyRepository facultyRepository;
    private final OrganizationService organizationService;
    private final UserDetailsServiceImpl userService;

    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(FacultiesController.class);

    @Autowired
    public FacultyService(FacultyMapper facultyMapper, FacultyRepository facultyRepository, OrganizationService organizationService, UserDetailsServiceImpl userService) {
        this.facultyMapper = facultyMapper;
        this.facultyRepository = facultyRepository;
        this.organizationService = organizationService;
        this.userService = userService;
    }

    public FacultyGetDto getFacultyById(Long facultyId) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(facultyId);

        if (facultyOptional.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract faculty entity with id " + facultyId);
        }
        Faculty faculty = facultyOptional.get();

        return facultyMapper.toDto(faculty);
    }

public FacultyGetDto createFaculty(FacultyPostDto facultyPostDto) {
    Faculty faculty = facultyMapper.toEntityFromPostDto(facultyPostDto);
    System.out.println("id of organisation =  " + facultyPostDto.getOrganizationId());
    organizationService.getOrganizationById(facultyPostDto.getOrganizationId());
    Faculty savedEntity = facultyRepository.save(faculty);
    FacultyGetDto res = facultyMapper.toDto(savedEntity);
    res.setOrganizationDto(organizationService.getOrganizationById(facultyPostDto.getOrganizationId()));
    LOGGER.info("Created Faculty with id = " + res.getFacId());

    return res;
}

    public List<FacultyGetDto> getAllFaculties(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Faculty> all = facultyRepository.findAll(pageable);
        List<FacultyGetDto> res = all.getContent().stream().map(facultyMapper::toDto).collect(Collectors.toList());

        return res;
    }

    public FacultyGetDto updateFaculty(long id, FacultyPostDto facultyPostDto) {
        Optional<Faculty> toUpdate = getFacultyEntity(id);
        Faculty entityToUpdate = toUpdate.get();
        String name = facultyPostDto.getName();


        if (name == null) {
            LOGGER.info("Request body not valid!");
            throw new NotValidJsonBodyException("Request body not valid!");
        }

        if (!Objects.equals(entityToUpdate.getName(), name)) {
            entityToUpdate.setName(name);
        }

        facultyRepository.save(entityToUpdate);

        return facultyMapper.toDto(entityToUpdate);
    }

    private Optional<Faculty> getFacultyEntity(long id) {
        Optional<Faculty> toUpdate = facultyRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Faculty with id {} not found", id);
            throw new EntityNotFoundException("Faculty not found");
        }

        return toUpdate;
    }

    public void deleteFaculty(long id) {
        Optional<Faculty> toDelete = getFacultyEntity(id);
        facultyRepository.delete(toDelete.get());
    }

    public FacultyGetDto addTeacher(long facId, long userId) {
        Optional<Faculty> optionalFaculty = getFacultyEntity(facId);
        Faculty toUpdate = optionalFaculty.get();
        List<User> facTeachers = toUpdate.getFacultyTeachers();
        User userToAdd = userService.getUserById(userId);

        if (facTeachers.contains(userToAdd)) {
            throw new UserNotAppropriateException("Student already added to course!");
        }
        if (!userToAdd.getRole().equals(RoleEnum.ROLE_TEACHER) &&
                !userToAdd.getRole().equals(RoleEnum.ROLE_COORDINATOR)) {
            throw new UserNotAppropriateException("User cannot be a teacher!");

        }
        facTeachers.add(userToAdd);
        toUpdate.setFacultyTeachers(facTeachers);

        return facultyMapper.toDto(facultyRepository.save(toUpdate));
    }

    public FacultyGetDto addCoordinator(long facId, long userId) {
        Optional<Faculty> optionalFaculty = getFacultyEntity(facId);
        Faculty toUpdate = optionalFaculty.get();
        List<User> facTeachers = toUpdate.getCoordinators();
        User userToAdd = userService.getUserById(userId);

        if (facTeachers.contains(userToAdd)) {
            throw new UserNotAppropriateException("Student already added to course!");
        }
        if (userToAdd.getRole().equals(RoleEnum.ROLE_COORDINATOR)) {
            facTeachers.add(userToAdd);
        } else if (userToAdd.getRole().equals(RoleEnum.ROLE_TEACHER)) {
            userToAdd.setRole(RoleEnum.ROLE_COORDINATOR);
            facTeachers.add(userToAdd);
        } else {
            throw new UserNotAppropriateException("User cannot be a fac coordinator");
        }
        toUpdate.setCoordinators(facTeachers);

        return facultyMapper.toDto(facultyRepository.save(toUpdate));
    }
}
