package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class FileGetSlimDto {
    @JsonProperty("fileId")
    private Long fileId;

    @JsonProperty("fileName")
    private String fileName;
}
