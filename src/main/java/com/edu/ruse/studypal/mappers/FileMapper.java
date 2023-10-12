package com.edu.ruse.studypal.mappers;

import com.edu.ruse.studypal.dtos.*;
import com.edu.ruse.studypal.entities.File;
import org.mapstruct.Mapper;

/**
 * @author anniexp
 */
@Mapper(componentModel = "spring")
public interface FileMapper {
  //  List<FileGetSlimDto> fileSetToFileGetSlimDtoSet(ArrayList<File> files);

    FileGetDto toDto(File file);

    FileGetSlimDto toSlimDto(File file);

    File toEntity(FileGetDto dto);

    File toEntityFromSlimDto(FileGetDto dto);

    FileGetDto toPostDto(File file);

    File toEntityFromPostDto(FilePostDto filePostDto);
}
