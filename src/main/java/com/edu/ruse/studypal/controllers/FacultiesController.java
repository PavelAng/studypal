package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.CourseGetDto;
import com.edu.ruse.studypal.dtos.FacultyGetDto;
import com.edu.ruse.studypal.dtos.FacultyPostDto;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.services.FacultyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author anniexp
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("faculties")
public class FacultiesController {
    private static final Logger LOGGER = LogManager.getLogger(OrganizationsController.class);
    private final FacultyService facultyService;

    @PostMapping(value = "/add", produces = "application/json")
    public ResponseEntity<FacultyGetDto> createFaculty(@RequestBody FacultyPostDto facultyPostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        FacultyGetDto body = facultyService.createFaculty(facultyPostDto);

        return new ResponseEntity<>(body,httpStatus);
    }

    @GetMapping
    public List<FacultyGetDto> getAllFaculties(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return facultyService.getAllFaculties(page);
    }

    @GetMapping("{id}")
    public FacultyGetDto getFacultyById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        FacultyGetDto res = facultyService.getFacultyById(id);
        if (res == null) {
            LOGGER.info("Faculty with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Faculty with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public FacultyGetDto updateFaculty(@PathVariable("id") long id, @RequestBody FacultyPostDto facultyPostDto) {
        try {
            return facultyService.updateFaculty(id, facultyPostDto);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Faculty with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Faculty not found");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            facultyService.deleteFaculty(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Faculty with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Faculty not found");
        }

        return new ResponseEntity<>(status);
    }

    @PutMapping("{id}/addTeacher")
    public FacultyGetDto addTeacherToFaculty(@PathVariable("id") long id, @RequestParam("userId") int userId) {
        try {
            return facultyService.addTeacher(id, userId);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Faculty with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Faculty not found");
        }
    }

    @PutMapping("{id}/addCoordinator")
    public FacultyGetDto addCoordinatorToFaculty(@PathVariable("id") long id, @RequestParam("userId") int userId) {
        try {
            return facultyService.addCoordinator(id, userId);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Faculty with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Faculty not found");
        }
    }
}
