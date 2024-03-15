package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.SubjectGetDto;
import com.edu.ruse.studypal.dtos.SubjectPostDto;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.services.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("subjects") // Define the base path for all mappings inside this controller
@Controller // This indicates that the class serves the role of a controller
public class SubjectsController {

    private static final Logger LOGGER = LogManager.getLogger(SubjectsController.class);
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectGetDto> createSubject(@RequestBody SubjectPostDto subjectPostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        SubjectGetDto body = subjectService.createSubject(subjectPostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    // Method to return JSON list of all subjects
    @GetMapping("/list")
    public ResponseEntity<List<SubjectGetDto>> getAllSubjectsJson(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        List<SubjectGetDto> subjects = subjectService.getAllSubjects(page);
        return ResponseEntity.ok(subjects);
    }

    // Method to return the subjects view
    @GetMapping
    public String getAllSubjectsView(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        List<SubjectGetDto> subjects = subjectService.getAllSubjects(page);
        model.addAttribute("subjects", subjects);
        return "subjects"; // Name of the Thymeleaf template
    }

    @GetMapping("{id}")
    public ResponseEntity<SubjectGetDto> getSubjectById(@PathVariable("id") long id) {
        try {
            SubjectGetDto subject = subjectService.getSubjectById(id);
            return ResponseEntity.ok(subject);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Subject with id {} was not found, returning 404.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<SubjectGetDto> updateSubject(@PathVariable("id") long id, @RequestBody SubjectPostDto subjectPostDto) {
        try {
            SubjectGetDto updatedSubject = subjectService.updateSubject(id, subjectPostDto);
            return ResponseEntity.ok(updatedSubject);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Subject with id {} was not found, caught exception {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Subject with id {} was not found, caught exception {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/userSubjects")
    public ResponseEntity<List<SubjectGetDto>> getUserSubjects(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        List<SubjectGetDto> subjects = subjectService.getUserSubjects(page);
        return ResponseEntity.ok(subjects);
    }

}
