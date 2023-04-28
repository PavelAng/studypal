package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.CoursesController;
import com.edu.ruse.studypal.dtos.CourseGetDto;
import com.edu.ruse.studypal.dtos.CoursePostDto;
import com.edu.ruse.studypal.entities.Course;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.mappers.CourseMapper;
import com.edu.ruse.studypal.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final DegreeService degreeService;
    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(CoursesController.class);

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, DegreeService degreeService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.degreeService = degreeService;
    }

    public CourseGetDto createCourse(CoursePostDto coursePostDto) {
        Course course = courseMapper.toEntityFromPostDto(coursePostDto);

        degreeService.getDegreeById(coursePostDto.getDegreeId());
        Course savedEntity = courseRepository.save(course);
        CourseGetDto res = courseMapper.toDto(savedEntity);
        res.setDegreeGetDto(degreeService.getDegreeById(coursePostDto.getDegreeId()));
        LOGGER.info("Created Course with id = " + res.getCourseId());

        return res;
    }

    public List<CourseGetDto> getAllCourses(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Course> all = courseRepository.findAll(pageable);
        List<CourseGetDto> res = all.getContent().stream().map(courseMapper::toDto).collect(Collectors.toList());

        return res;
    }

    public CourseGetDto getCourseById(long id) {
        Optional<Course> optional = courseRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract course entity with id " + id);
        }
        Course course = optional.get();
        CourseGetDto res = courseMapper.toDto(course);
        res.setDegreeGetDto(degreeService.getDegreeById(course.getCourseId()));

        return res;
    }

    public CourseGetDto updateCourse(long id, CoursePostDto coursePostDto) {
        return null;
    }

    public void deleteCourse(long id) {
        Optional<Course> toDelete = getCourseEntity(id);
        courseRepository.delete(toDelete.get());
    }

    private Optional<Course> getCourseEntity(long id) {
        Optional<Course> toUpdate = courseRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Course with id {} not found", id);
            throw new EntityNotFoundException("Course not found");
        }

        return toUpdate;
    }
}
