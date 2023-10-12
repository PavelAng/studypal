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
public class FileGetDto {
    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("filePath")
    private String filePath;
}
