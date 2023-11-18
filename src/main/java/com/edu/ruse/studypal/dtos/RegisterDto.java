package com.edu.ruse.studypal.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
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
public class RegisterDto {
   // private Long user_id;

    @Size(max = 120)
    @JsonProperty("password")
    private String password;

    @Size(max = 20)
    @JsonProperty("username")
    private String username;

    @JsonProperty("role")
    private String role;
}
