package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectGetDto {
    @JsonProperty("subjectId")
    private Long subjectId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("course")
    private CourseGetDto courseGetDto;
    @JsonProperty("topics")
    private List<String> topics;
}
