package com.edu.ruse.studypal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminPanel")
    public String admin() {
        return "adminPanel";
    }

    @GetMapping("/student/add")
    public String addStudent(Model model) {
        // Add your code here to add a new student
        return "redirect:/adminPanel";
    }

    @GetMapping("/student/edit")
    public String editStudent(Model model) {
        // Add your code here to edit an existing student
        return "redirect:/adminPanel";
    }

    @GetMapping("/student/delete")
    public String deleteStudent(Model model) {
        // Add your code here to delete an existing student
        return "redirect:/adminPanel";
    }

    @GetMapping("/admin-faculty/add")
    public String addAdminFaculty(Model model) {
        // Add your code here to add a new admin faculty
        return "redirect:/adminPanel";
    }

    @GetMapping("/admin-faculty/edit")
    public String editAdminFaculty(Model model) {
        // Add your code here to edit an existing admin faculty
        return "redirect:/adminPanel";
    }

    @GetMapping("/admin-faculty/delete")
    public String deleteAdminFaculty(Model model) {
        // Add your code here to delete an existing admin faculty
        return "redirect:/adminPanel";
    }

    @GetMapping("/teacher/add")
    public String addTeacher(Model model) {
        // Add your code here to add a new teacher
        return "redirect:/adminPanel";
    }

    @GetMapping("/teacher/edit")
    public String editTeacher(Model model) {
        // Add your code here to edit an existing teacher
        return "redirect:/adminPanel";
    }

    @GetMapping("/teacher/delete")
    public String deleteTeacher(Model model) {
        // Add your code here to delete an existing teacher
        return "redirect:/adminPanel";
    }

    @GetMapping("/event/add")
    public String addEvent(Model model) {
        // Add your code here to add a new event
        return "redirect:/adminPanel";
    }

    @GetMapping("/event/edit")
    public String editEvent(Model model) {
        // Add your code here to edit an existing event
        return "redirect:/adminPanel";
    }

    @GetMapping("/event/delete")
    public String deleteEvent(Model model) {
        // Add your code here to delete an existing event
        return "redirect:/adminPanel";
    }

    @GetMapping("/discipline/add")
    public String addDiscipline(Model model) {
        // Add your code here to add a new discipline
        return "redirect:/adminPanel";
    }

    @GetMapping("/discipline/edit")
    public String editDiscipline(Model model) {
        // Add your code here to edit an existing discipline
        return "redirect:/adminPanel";
    }

    @GetMapping("/discipline/delete")
    public String deleteDiscipline(Model model) {
        // Add your code here to delete an existing discipline
        return "redirect:/adminPanel";
    }

    @GetMapping("/group/add")
    public String addGroup(Model model) {
        // Add your code here to add a new group
        return "redirect:/adminPanel";
    }

    @GetMapping("/group/edit")
    public String editGroup(Model model) {
        // Add your code here to edit an existing group
        return "redirect:/adminPanel";
    }

    @GetMapping("/group/delete")
    public String deleteGroup(Model model) {
        // Add your code here to delete an existing group
        return "redirect:/adminPanel";
    }

}
