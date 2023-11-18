package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventGetDto {
    @JsonProperty("eventId")
    private Long eventId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("subject")
    private SubjectGetDto subjectGetDto;
    @JsonProperty("eventStart")
    private Instant eventStart;
    @JsonProperty("eventEnd")
    private Instant eventEnd;
    @JsonProperty("type")
    private String type;
    @JsonProperty("eventMaterials")
    private List<FileGetDto> eventMaterials;
    @JsonProperty("eventExercises")
    private List<FileGetDto> eventExercises;
    @JsonProperty("eventSolutions")
    private List<FileGetDto> eventSolutions;
}
