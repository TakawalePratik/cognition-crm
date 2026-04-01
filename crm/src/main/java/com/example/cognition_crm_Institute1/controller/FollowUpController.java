package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.EnquiryFollowUp;
import com.example.cognition_crm_Institute1.repository.EnquiryFollowUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@Controller
@RequestMapping("/followups")
public class FollowUpController {

    @Autowired
    private EnquiryFollowUpRepository repo;

    // TODAY
    @GetMapping("/today")
    public String today(Model model) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23,59,59);

        List<EnquiryFollowUp> list = repo.findByNextFollowupDateBetween(start, end);

        model.addAttribute("followups", list);
        model.addAttribute("type", "Today");
        return "followup/list";
    }

    // OVERDUE
    @GetMapping("/overdue")
    public String overdue(Model model) {
        List<EnquiryFollowUp> list = repo.findByNextFollowupDateBefore(LocalDateTime.now());

        model.addAttribute("followups", list);
        model.addAttribute("type", "Overdue");
        return "followup/list";
    }

    // UPCOMING
    @GetMapping("/upcoming")
    public String upcoming(Model model) {
        List<EnquiryFollowUp> list = repo.findByNextFollowupDateAfter(LocalDateTime.now());

        model.addAttribute("followups", list);
        model.addAttribute("type", "Upcoming");
        return "followup/list";
    }
}