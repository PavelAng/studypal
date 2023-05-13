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
public class FacultyGetDto {
    @JsonProperty("fac_id")
    private Long facId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("organization")
    private OrganizationDto organizationDto;

    @JsonProperty("coordinator")
    private Long coordinator;
}