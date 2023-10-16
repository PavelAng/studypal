package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.EventGetDto;
import com.edu.ruse.studypal.dtos.EventPostDto;
import com.edu.ruse.studypal.exceptions.NotFoundOrganizationException;
import com.edu.ruse.studypal.services.EventService;
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
@RequestMapping("events")
@Controller
@RestController
public class EventsController {
    /*@GetMapping("/events")
    public String showEvents(Model model) {

        List<String> subjects = Arrays.asList("Math", "basic of c++");
        model.addAttribute("subjects", subjects);

        List<String> homework = Arrays.asList("Math homework", "basic of c++ task");
        model.addAttribute("homework", homework);

        List<String> exams = Arrays.asList("Math exam", "basic of c++ test");
        model.addAttribute("exams", exams);

        return "events";
    }*/
    private static final Logger LOGGER = LogManager.getLogger(SubjectsController.class);
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventGetDto> createEvent(@RequestBody EventPostDto eventPostDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        EventGetDto body = eventService.createEvent(eventPostDto);

        return new ResponseEntity<>(body, httpStatus);
    }

    @GetMapping
    public List<EventGetDto> getAllEvents(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        return eventService.getAllEvents(page);
    }

    @GetMapping("{id}")
    public EventGetDto getEventById(@PathVariable("id") long id, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        EventGetDto res = eventService.getEventById(id);
        if (res == null) {
            LOGGER.info("Event with id {} was not found, returning 404.", id);
            throw new NotFoundOrganizationException("Event with id " + id + " was not found");
        }

        return res;
    }

    @PutMapping("{id}")
    public EventGetDto updateEvent(@PathVariable("id") long id, @RequestBody EventPostDto eventPostDto) {
        try {
            return eventService.updateEvent(id, eventPostDto);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Event with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Event not found");
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.GONE;
        try {
            eventService.deleteEvent(id);
        } catch (EntityNotFoundException e) {
            LOGGER.debug("Event with id {} was not found, caught exception {}", id, e);
            LOGGER.debug("Cause :", e);
            throw new NotFoundOrganizationException("Event not found");
        }

        return new ResponseEntity<>(status);
    }
    //to do
    // add add Add Material To Event method
    // add add Add Exercise To Event method
    // add add Add Solution To Event method

    //4 - make a get all events for logged user
    @GetMapping("/userEvents")
    public List<EventGetDto> getLoggedUserEvents() {
        return eventService.getUserEvents();
    }

}

