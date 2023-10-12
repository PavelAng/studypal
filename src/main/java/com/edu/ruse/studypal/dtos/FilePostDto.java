package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class FilePostDto {
    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("filePath")
    private String filePath;
}
