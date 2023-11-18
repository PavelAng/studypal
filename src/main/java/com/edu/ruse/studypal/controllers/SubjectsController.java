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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("subjects")
@Controller
@RestController
public class SubjectsController {

   /* @GetMapping("/subjects")
    public String subjects() {

        return "subjects.html";
    }*/
   private static final Logger LOGGER = LogManager.getLogger(SubjectsController.class);
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectGetDto> createSubject(@RequestBody SubjectPostDto subjectPostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        SubjectGetDto body = subjectService.createSubject(subjectPostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping
    public List<SubjectGetDto> getAllSubjects(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return subjectService.getAllSubjects(page);
    }

    @GetMapping("{id}")
    public SubjectGetDto getSubjectById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        SubjectGetDto res = subjectService.getSubjectById(id);
        if (res == null) {
            LOGGER.info("Subject with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Subject with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public SubjectGetDto updateSubject(@PathVariable("id") long id, @RequestBody SubjectPostDto subjectPostDto) {
        try {
            return subjectService.updateSubject(id, subjectPostDto);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Subject with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Subject not found");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            subjectService.deleteSubject(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Subject with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Subject not found");
        }

        return new ResponseEntity<>(status);
    }

    //to do
    //1 - make a get all student's course's subjects - done
    //2 - make a get all teachers reached subjects - done
    //3 - make  only teachers who teach a subject to be able to create-edit-delete events - done
    //4 - make a get all events for logged user - done
    @GetMapping("/userSubjects")
    public List<SubjectGetDto> getUserSubjects(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return subjectService.getUserSubjects(page);
    }

}