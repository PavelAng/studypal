package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.SubjectGetDto;
import com.edu.ruse.studypal.dtos.SubjectPostDto;
import com.edu.ruse.studypal.entities.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "courseGetDto", source = "course")
    SubjectGetDto toDto(Subject subject);

    Subject toEntity(SubjectGetDto subjectGetDto);

    SubjectPostDto toPostDto(Subject subject);

    @Mapping(target = "course.courseId", source = "courseId")
    Subject toEntityFromPostDto(SubjectPostDto subjectPostDto);
}
