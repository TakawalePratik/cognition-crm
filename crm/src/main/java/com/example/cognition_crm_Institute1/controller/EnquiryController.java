package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.Enquiry;
import com.example.cognition_crm_Institute1.service.EnquiryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/enquiry")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    // Show all enquiries
    @GetMapping
    public String list(Model model) {
        List<Enquiry> enquiries = enquiryService.getAllEnquiries();
        model.addAttribute("enquiries", enquiries);
        return "enquiry/list";
    }

    // Add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "enquiry/add";
    }

    // Save enquiry
    @PostMapping("/save")
    public String save(@ModelAttribute Enquiry enquiry) {
        enquiryService.saveEnquiry(enquiry);
        return "redirect:/enquiry";
    }

    // Edit form
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Enquiry> enquiry = enquiryService.getEnquiryById(id);
        if (enquiry.isPresent()) {
            model.addAttribute("enquiry", enquiry.get());
            return "enquiry/edit";
        }
        return "redirect:/enquiry";
    }

    // Update enquiry
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Enquiry enquiry) {
        enquiryService.updateEnquiry(id, enquiry);
        return "redirect:/enquiry";
    }

    // Update status
    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               @RequestParam String remarks) {
        enquiryService.updateStatus(id, status, remarks);
        return "redirect:/enquiry";
    }

    // Delete enquiry
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        enquiryService.deleteEnquiry(id);
        return "redirect:/enquiry";
    }

    // View by status
    @GetMapping("/status/{status}")
    public String viewByStatus(@PathVariable String status, Model model) {
        List<Enquiry> enquiries = enquiryService.getEnquiriesByStatus(status);
        model.addAttribute("enquiries", enquiries);
        model.addAttribute("status", status);
        return "enquiry/list";
    }
}