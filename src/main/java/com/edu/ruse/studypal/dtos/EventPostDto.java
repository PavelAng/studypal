package com.edu.ruse.studypal.dtos;

import com.edu.ruse.studypal.entities.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Timestamp;
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
public class EventPostDto {
    @JsonProperty("type")
    private EventTypeEnum type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventStart")
    @Timestamp
    private Instant eventStart;

    @JsonProperty("eventEnd")
    @Timestamp
    private Instant eventEnd;

    @JsonProperty("subject_id")
    private Long subjectId;
}
