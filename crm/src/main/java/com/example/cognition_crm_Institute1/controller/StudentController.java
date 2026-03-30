package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.Student;
import com.example.cognition_crm_Institute1.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Show all students
    @GetMapping
    public String list(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student/list";
    }

    // Add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    // Save student
    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/student";
    }

    // Edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "student/edit";
        }
        return "redirect:/student";
    }

    // Update student
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student student) {
        studentService.updateStudent(id, student);
        return "redirect:/student";
    }

    // Delete student
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }

    // View student details
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "student/view";
        }
        return "redirect:/student";
    }
}