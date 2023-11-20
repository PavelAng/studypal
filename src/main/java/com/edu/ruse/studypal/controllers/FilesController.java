package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.*;
import com.edu.ruse.studypal.entities.File;
import com.edu.ruse.studypal.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
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

//    @GetMapping
//    public ResponseEntity<List<FileGetSlimDto>> getAllFiles(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
//        return new ResponseEntity<>(fileService.getAllFiles(page), HttpStatus.OK);
//    }

    @GetMapping
    public ModelAndView getAllFilles(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        ModelAndView getAllFiles = new ModelAndView();
        getAllFiles.addObject("files", fileService.getAllFiles(page));
        return getAllFiles;
    }

    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody FilePostDto filePostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        File body = fileService.createFile(filePostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    /**
     * gets file, file is downloaded when link is loaded
     *
     * @throws IOException
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws FileNotFoundException {
        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileTypee()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName())
                .body(new ByteArrayResource(file.getFileContent()));
    }

    /**
     * open file in browser
     *
     * @param id - id of file
     */
    @GetMapping("/{id}/open")
    public void showFileContentInBrowser(
            //@Param("id") Long id,
            @PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));

        response.setContentType(file.getFileTypee());
        //  + ", image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(file.getFileContent());
        response.addHeader("content-disposition", "inline");
        response.getOutputStream().close();
    }
}
