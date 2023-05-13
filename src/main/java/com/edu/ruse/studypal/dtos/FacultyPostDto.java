package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class FacultyPostDto {
    @JsonProperty("name")
    private String name;

    @JsonProperty("organization")
    private Long organizationId;

    @JsonProperty("coordinator")
    private Long coordinator;
}
