package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.AuthController;
import com.edu.ruse.studypal.controllers.FacultiesController;
import com.edu.ruse.studypal.dtos.SubjectGetDto;
import com.edu.ruse.studypal.dtos.SubjectPostDto;
import com.edu.ruse.studypal.entities.Course;
import com.edu.ruse.studypal.entities.Subject;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.exceptions.NotValidJsonBodyException;
import com.edu.ruse.studypal.mappers.CourseMapper;
import com.edu.ruse.studypal.mappers.SubjectMapper;
import com.edu.ruse.studypal.repositories.SubjectRepository;
import com.edu.ruse.studypal.security.jwt.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;
    private final CourseMapper courseMapper;
    @Autowired
    private JwtUtils jwtUtils;


    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(FacultiesController.class);

    public SubjectService(SubjectMapper subjectMapper, SubjectRepository subjectRepository, CourseService courseService, CourseMapper courseMapper) {
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    public SubjectGetDto createSubject(SubjectPostDto subjectPostDto) {
        Subject subject = subjectMapper.toEntityFromPostDto(subjectPostDto);

        courseService.getCourseById( subjectPostDto.getCourseId());
        Subject savedEntity = subjectRepository.save(subject);
        SubjectGetDto res =  subjectMapper.toDto(savedEntity);
        res.setCourseGetDto(courseService.getCourseById(subjectPostDto.getCourseId()));
        LOGGER.info("Created Subject with id = " + res.getSubjectId());

        return res;
    }

    public List<SubjectGetDto> getAllSubjects(int page) {
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        Page<Subject> all = subjectRepository.findAll(pageable);
        List<SubjectGetDto> res = all.getContent().stream().map(subjectMapper::toDto).collect(Collectors.toList());

        return res;
    }

    public SubjectGetDto getSubjectById(long id) {
        Optional<Subject> optional = subjectRepository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundOrganizationException("Could not extract course entity with id " + id);
        }
        Subject subject = optional.get();
        SubjectGetDto res = subjectMapper.toDto(subject);
        res.setCourseGetDto(courseService.getCourseById(subject.getCourse().getCourseId()));

        return res;
    }


    public SubjectGetDto updateSubject(long id, SubjectPostDto subjectPostDto) {
        Optional<Subject> toUpdate = getSubjectEntity(id);
        Subject entityToUpdate = toUpdate.get();
        String name = subjectPostDto.getName();
        String desc = subjectPostDto.getName();
        long courseId = subjectPostDto.getCourseId();

        if (name == null) {
            LOGGER.info("Request body not valid!");
            throw new NotValidJsonBodyException("Request body not valid!");
        }

        if (!Objects.equals(entityToUpdate.getName(), name)) {
            entityToUpdate.setName(name);
        }
        if (!Objects.equals(entityToUpdate.getDescription(), desc)) {
            entityToUpdate.setDescription(desc);

        } if (!Objects.equals(entityToUpdate.getCourse().getCourseId(), courseId)) {
            entityToUpdate.setCourse(courseMapper.toEntity(courseService.getCourseById(courseId)));
        }

        subjectRepository.save(entityToUpdate);

        return subjectMapper.toDto(entityToUpdate);
    }

    public void deleteSubject(long id) {
        Optional<Subject> toDelete = getSubjectEntity(id);
        subjectRepository.delete(toDelete.get());
    }

    private Optional<Subject> getSubjectEntity(long id) {
        Optional<Subject> toUpdate = subjectRepository.findById(id);

        if (toUpdate.isEmpty()) {
            LOGGER.error("Subject with id {} not found", id);
            throw new EntityNotFoundException("Subject not found");
        }

        return toUpdate;
    }

    public List<SubjectGetDto> getUserSubjects() {
        String jwt = AuthController.jwt;
        List<Course> userCourses = jwtUtils.getUserCourse(jwt);
        List<Subject> subjectList = userCourses.stream().findFirst().map(Course::getSubjectsList).get();
        List<SubjectGetDto> res = subjectList.stream().map(subjectMapper::toDto).toList();

        return res;
    }
}
