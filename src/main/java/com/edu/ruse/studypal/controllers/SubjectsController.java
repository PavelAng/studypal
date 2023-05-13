package com.edu.ruse.studypal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubjectsController {

    @GetMapping("/subjects")
    public String subjects() {

        return "subjects.html";
    }
}