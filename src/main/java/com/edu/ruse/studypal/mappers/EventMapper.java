package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.EventGetDto;
import com.edu.ruse.studypal.dtos.EventPostDto;
import com.edu.ruse.studypal.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "subjectGetDto", source = "subject")
    @Mapping(target = "type", source = "type")
    EventGetDto toDto(Event event);

    @Mapping(target = "subject", source = "subjectGetDto")
    Event toEntity(EventGetDto eventGetDto);

    EventPostDto toPostDto(Event event);

    @Mapping(target = "subject.subjectId", source = "subjectId")
    Event toEntityFromPostDto(EventPostDto eventPostDto);
}
