package com.edu.ruse.studypal.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Arrays;

@Controller
public class EventsController {

    @GetMapping("/events")
    public String showEvents(Model model) {

        List<String> subjects = Arrays.asList("Math", "basic of c++");
        model.addAttribute("subjects", subjects);

        List<String> homework = Arrays.asList("Math homework", "basic of c++ task");
        model.addAttribute("homework", homework);

        List<String> exams = Arrays.asList("Math exam", "basic of c++ test");
        model.addAttribute("exams", exams);

        return "events";
    }
}

