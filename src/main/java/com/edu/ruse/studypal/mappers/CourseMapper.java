package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.CourseGetDto;
import com.edu.ruse.studypal.dtos.CoursePostDto;
import com.edu.ruse.studypal.entities.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseGetDto toDto(Course course);

    Course toEntity(CourseGetDto courseGetDto);

    CoursePostDto toPostDto(Course course);

    @Mapping(target = "degree.degreeId", source = "degreeId")
   Course toEntityFromPostDto(CoursePostDto coursePostDto);
}
