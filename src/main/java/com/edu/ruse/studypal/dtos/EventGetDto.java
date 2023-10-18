package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
}
