package com.edu.ruse.studypal.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author anniexp
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
