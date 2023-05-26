package com.edu.ruse.studypal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "login.html";
    }

    @PostMapping("/login")
    public String processLogin() {
        // Handle login logic here
        // You can validate the user credentials and redirect to the appropriate page
        // or perform any necessary actions
        return "redirect:/dashboard"; // Replace "/dashboard" with your desired landing page after login
    }
}
