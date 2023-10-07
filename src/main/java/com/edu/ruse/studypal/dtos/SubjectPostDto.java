package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class SubjectPostDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("courseId")
    private Long courseId;
    @JsonProperty("topics")
    private List<String> topics;
}
