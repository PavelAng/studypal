package com.edu.ruse.studypal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/files")
    public String showFilesPage() {
        return "files";
    }
}