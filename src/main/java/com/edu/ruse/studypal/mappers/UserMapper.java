package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.LoginDto;
import com.edu.ruse.studypal.dtos.RegisterDto;
import com.edu.ruse.studypal.entities.User;
import org.mapstruct.Mapper;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    LoginDto toDto(User user);
    User toEntity(LoginDto loginDto);

    RegisterDto toPostDto(User user);
    User toEntityFromPostDto(RegisterDto registerDto);
}
