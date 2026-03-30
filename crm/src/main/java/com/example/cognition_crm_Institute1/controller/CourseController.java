package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.Course;
import com.example.cognition_crm_Institute1.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Show all courses
    @GetMapping
    public String list(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    // Add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/add";
    }

    // Save course
    @PostMapping("/save")
    public String save(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/course";
    }

    // Edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            return "course/edit";
        }
        return "redirect:/course";
    }

    // Update course
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Course course) {
        courseService.updateCourse(id, course);
        return "redirect:/course";
    }

    // Delete course
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }
}