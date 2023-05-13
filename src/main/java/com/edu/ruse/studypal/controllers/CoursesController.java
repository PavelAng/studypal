package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.CourseGetDto;
import com.edu.ruse.studypal.dtos.CoursePostDto;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.services.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller defining a RESTful API for courses.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
public class CoursesController {
    private static final Logger LOGGER = LogManager.getLogger(CoursesController.class);
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseGetDto> createDegree(@RequestBody CoursePostDto coursePostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        CourseGetDto body = courseService.createCourse(coursePostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping
    public List<CourseGetDto> getAllCourses(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return courseService.getAllCourses(page);
    }

    @GetMapping("{id}")
    public CourseGetDto getCourseById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        CourseGetDto res = courseService.getCourseById(id);
        if (res == null) {
            LOGGER.info("Course with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Course with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public CourseGetDto updateCourse(@PathVariable("id") long id, @RequestBody CoursePostDto coursePostDto) {
        try {
            return courseService.updateCourse(id, coursePostDto);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Course with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Course not found");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            courseService.deleteCourse(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Course with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Course not found");
        }

        return new ResponseEntity<>(status);
    }
}
