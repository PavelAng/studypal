package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.*;
import com.edu.ruse.studypal.services.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author anniexp
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("files")
public class FilesController {
    private static final Logger LOGGER = LogManager.getLogger(FilesController.class);
    private final FileService fileService;

    @GetMapping
    public List<FileGetSlimDto> getAllFiles(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return fileService.getAllFiles(page);
    }

    @PostMapping
    public ResponseEntity<FileGetDto> createFile(@RequestBody FilePostDto filePostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        FileGetDto body = fileService.createFile(filePostDto);

        return new ResponseEntity<>(body, httpStatus);
    }
}
