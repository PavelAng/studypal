package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.DegreeGetDto;
import com.edu.ruse.studypal.dtos.DegreePostDto;
import com.edu.ruse.studypal.entities.Degree;
import com.edu.ruse.studypal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface DegreeMapper {
    @Mapping(target = "facultyGetDto.name", source = "faculty.name")
    DegreeGetDto toDto(Degree degree);

    Degree toEntity(DegreeGetDto degreeGetDto);

    DegreePostDto toPostDto(Degree degree);

    @Mapping(target = "faculty.facId", source = "facultyId")
    Degree toEntityFromPostDto(DegreePostDto degreePostDto);
}
