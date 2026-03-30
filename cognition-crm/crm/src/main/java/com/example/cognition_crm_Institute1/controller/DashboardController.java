package com.example.cognition_crm_Institute1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.cognition_crm_Institute1.service.CourseService;
import com.example.cognition_crm_Institute1.service.EnquiryService;
import com.example.cognition_crm_Institute1.service.PaymentService;
import com.example.cognition_crm_Institute1.service.StudentService;

@Controller
public class DashboardController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Get Total Enquiries
        long totalEnquiries = enquiryService.getAllEnquiries().size();
        model.addAttribute("totalEnquiries", totalEnquiries);

        // Get Active Students
        long activeStudents = studentService.getStudentsByStatus("Active").size();
        model.addAttribute("activeStudents", activeStudents);

        // Get Active Courses
        long activeCourses = courseService.getActiveCourses().size();
        model.addAttribute("activeCourses", activeCourses);

        // Get Pending Payments (Sum of all pending amounts)
        double pendingPayments = paymentService.getPendingPayments()
                .stream()
                .mapToDouble(p -> p.getAmount())
                .sum();
        model.addAttribute("pendingPayments", pendingPayments);

        return "dashboard";
        
    }
}