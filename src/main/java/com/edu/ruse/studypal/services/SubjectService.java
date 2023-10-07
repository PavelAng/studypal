package com.edu.ruse.studypal.services;

import com.edu.ruse.studypal.controllers.FacultiesController;
import com.edu.ruse.studypal.mappers.SubjectMapper;
import com.edu.ruse.studypal.repositories.SubjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author anniexp
 */
@Service
public class SubjectService {
    private final SubjectMapper subjectMapper;
    private final SubjectRepository subjectRepository;
    private final CourseService courseService;

    private static final int PAGE_SIZE = 2;
    private static final Logger LOGGER = LogManager.getLogger(FacultiesController.class);

    public SubjectService(SubjectMapper subjectMapper, SubjectRepository subjectRepository, CourseService courseService) {
        this.subjectMapper = subjectMapper;
        this.subjectRepository = subjectRepository;
        this.courseService = courseService;
    }
}
