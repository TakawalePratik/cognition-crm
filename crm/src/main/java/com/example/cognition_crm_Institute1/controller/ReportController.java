package com.example.cognition_crm_Institute1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.cognition_crm_Institute1.service.ReportService;

import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("total", reportService.getTotalEnquiries());
        model.addAttribute("converted", reportService.getConvertedEnquiries());
        model.addAttribute("rate", reportService.getConversionRate());
        return "reports/dashboard";
    }

    @GetMapping("/status")
    @ResponseBody
    public Map<String, Long> statusReport() {
        return reportService.getStatusReport();
    }

    @GetMapping("/daily")
    @ResponseBody
    public Map<String, Long> dailyReport() {
        return reportService.getDailyReport();
    }

    @GetMapping("/course")
    @ResponseBody
    public Map<String, Long> courseReport() {
        return reportService.getCourseReport();
    }
}
