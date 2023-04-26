package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.DegreeGetDto;
import com.edu.ruse.studypal.dtos.DegreePostDto;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.services.DegreeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller defining a RESTful API for degrees.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("degrees")
public class DegreesController {
    private static final Logger LOGGER = LogManager.getLogger(DegreesController.class);
    private final DegreeService degreeService;

    @PostMapping
    public ResponseEntity<DegreeGetDto> createDegree(@RequestBody DegreePostDto degreePostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        DegreeGetDto body = degreeService.createDegree(degreePostDto);


        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping
    public List<DegreeGetDto> getAllDegrees(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return degreeService.getAllDegrees(page);
    }

    @GetMapping("{id}")
    public DegreeGetDto getDegreeById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        DegreeGetDto res = degreeService.getDegreeById(id, page);
        if (res == null) {
            LOGGER.info("Degree with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Degree with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public DegreeGetDto updateDegree(@PathVariable("id") long id, @RequestBody DegreePostDto degreePostDto) {
        try {
            return degreeService.updateDegree(id, degreePostDto);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Degree with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Degree not found");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteDegree(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            degreeService.deleteDegree(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Degree with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Degree not found");
        }

        return new ResponseEntity<>(status);
    }
}
