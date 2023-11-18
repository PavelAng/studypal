package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.CoursesController;
import com.edu.ruse.studypal.dtos.CourseGetDto;
import com.edu.ruse.studypal.dtos.CoursePostDto;
import com.edu.ruse.studypal.entities.Course;
import com.edu.ruse.studypal.entities.RoleEnum;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.exceptions.UserNotAppropriateException;
import com.edu.ruse.studypal.mappers.CourseMapper;
import com.edu.ruse.studypal.repositories.CourseRepository;
import com.edu.ruse.studypal.security.services.UserDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final DegreeService degreeService;
    private static final int PAGE_SIZE = 10;
    private static final Logger LOGGER = LogManager.getLogger(CoursesController.class);
    private final UserDetailsServiceImpl userService;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper, DegreeService degreeService, UserDetailsServiceImpl userService) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.degreeService = degreeService;
        this.userService = userService;
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
        //??????????????????
        res.setDegreeGetDto(degreeService.getDegreeById(course.getDegree().getDegreeId()));

        return res;
    }
    //?????????????????? to do
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

    public CourseGetDto addStudentToCourse(long courseId, long userId) {
        Optional<Course> optionalCourse = getCourseEntity(courseId);
        Course toUpdate =  optionalCourse.get();
        List<User> students = toUpdate.getCourseStudents();

        User userToAdd = userService.getUserById(userId);
        if (students.contains(userToAdd)) {
            throw new UserNotAppropriateException("Student already added to course!");
        }
        if (!userToAdd.getRole().equals(RoleEnum.ROLE_STUDENT)) {
            throw new UserNotAppropriateException("User is not a student!");
        }
        students.add(userToAdd);
        toUpdate.setCourseStudents(students);
        courseRepository.save(toUpdate);
        System.out.println(toUpdate);

        return courseMapper.toDto(toUpdate);
    }
}
