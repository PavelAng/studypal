package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.entities.RoleEnum;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.repositories.UserRepository;
import com.edu.ruse.studypal.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam("userId") Long userId,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(RoleEnum.valueOf(role));
            userRepository.save(user);
        }
        return "redirect:/users"; // Redirect back to the user list page
    }
}
