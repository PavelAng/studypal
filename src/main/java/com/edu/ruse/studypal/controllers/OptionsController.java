package com.edu.ruse.studypal.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OptionsController {

    @GetMapping("/options")
    public String options(Model model) {
        return "options";
    }

    @PostMapping("/options")
    public String savePassword(@RequestParam("old-password") String oldPassword,
                               @RequestParam("new-password") String newPassword,
                               @RequestParam("confirm-password") String confirmPassword,
                               Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match");
            return "options";
        }

        // save the new password

        return "redirect:/";
    }

}
