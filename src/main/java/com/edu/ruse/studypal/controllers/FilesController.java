package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.*;
import com.edu.ruse.studypal.entities.File;
import com.edu.ruse.studypal.services.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<FileGetSlimDto>> getAllFiles(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return new ResponseEntity<>(fileService.getAllFiles(page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody FilePostDto filePostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        File body = fileService.createFile(filePostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    /*@GetMapping("/{id}")
    public ModelAndView getById(
            @PathVariable(value = "id") long id) throws FileNotFoundException {

        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));
        //make it from link to show the buff image pls
        String linkToSource = file.getFilePath();

        return new ModelAndView("redirect:" + linkToSource);
    }*/

    /**
     * gets file, file is downloaded when link is loaded
     *
     * @throws IOException
     */
    @GetMapping("/{id}/download")
    public void showImage(
            // @Param("id") Long id,
            @PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf, application/pdf");
        response.getOutputStream().write(file.getFileContent());
        response.addHeader("content-disposition", "inline; filename=\"" + file.getFileName());
        response.getOutputStream().close();
    }

    /**
     * open file in browser
     *
     * @param id - id of file
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/{id}/open")
    public void showFileContentInBrowser(
            //@Param("id") Long id,
            @PathVariable(value = "id") long id, HttpServletResponse response) throws IOException {
        File file = fileService.findById(id).orElseThrow(() -> new FileNotFoundException("File Not Found"));
        System.out.println(file);

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf, application/pdf");
        response.getOutputStream().write(file.getFileContent());
        response.addHeader("content-disposition", "inline");
        response.getOutputStream().close();
    }
}
