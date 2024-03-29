package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.OrganizationDto;
import com.edu.ruse.studypal.dtos.OrganizationPostDto;
import com.edu.ruse.studypal.entities.Organization;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.repositories.OrganizationRepository;
import com.edu.ruse.studypal.repositories.UserRepository;
import com.edu.ruse.studypal.services.OrganizationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Controller defining a RESTful API for organizations.
 * @author anniexp
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("organizations")
public class OrganizationsController {


    OrganizationRepository organizationRepository;
    private static final Logger LOGGER = LogManager.getLogger(OrganizationsController.class);
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(OrganizationPostDto organizationPostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        OrganizationDto body = organizationService.createOrganization(organizationPostDto);

        return new ResponseEntity<>(body,httpStatus);
    }

//    @GetMapping
//    public List<OrganizationDto> getAllOrganizations(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
//        return organizationService.getAllOrganizations(page);
//    }

    @GetMapping
    public ModelAndView getAllOrganization(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        ModelAndView getAllOrganization = new ModelAndView();
        getAllOrganization.addObject("organizations", organizationService.getAllOrganizations(page));
        return getAllOrganization;
    }

//    @GetMapping("/organizations")
//    public String getOrganizations(Model model) {
//        List<Organization> organizations = organizationRepository.findAll();
//        model.addAttribute("organizations", organizations);
//        return "organizations";
//    }

    @GetMapping("{id}")
    public OrganizationDto getOrganizationById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        OrganizationDto res = organizationService.getOrganizationById(id);
        if (res == null) {
            LOGGER.info("Organization with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Organization with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public OrganizationDto updateOrganization(@PathVariable("id") long id, @RequestBody OrganizationPostDto organizationPostDto) {
        try {
            return organizationService.updateOrganization(id,organizationPostDto);
        } catch (EntityNotFoundException | IllegalAccessException e) {
            LOGGER.debug("Organization with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Organization not found");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            organizationService.deleteOrganization(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Organization with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Organization not found");
        }

        return new ResponseEntity<>(status);
    }
}
