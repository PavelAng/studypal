package com.edu.ruse.studypal.dtos;

import com.edu.ruse.studypal.entities.RoleEnum;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author anniexp
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    private Long user_id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 64, name = "password")
    private String password;

    @Size(max = 20)
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "role", nullable = false)
    private RoleEnum role;
}
