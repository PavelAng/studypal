package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrganizationDto {
    @JsonProperty("organizationId")
    private Long organizationId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
