package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrganizationPostDto {
    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "description", required = true)
    private String description;

    @JsonProperty("admin_org_id")
    private Long adminOrg;
}
