package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.*;
import com.example.cognition_crm_Institute1.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/crm")
public class CRMFlowController {

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private EnquiryFollowUpService followUpService;

    @Autowired
    private StudentEnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PaymentService paymentService;

    // ==================== ENQUIRY FOLLOW-UP MANAGEMENT ====================

    // View enquiry with follow-ups
    @GetMapping("/enquiry/{id}")
    public String viewEnquiryFlow(@PathVariable Long id, Model model) {
        Optional<Enquiry> enquiry = enquiryService.getEnquiryById(id);
        if (enquiry.isPresent()) {
            model.addAttribute("enquiry", enquiry.get());
            
            List<EnquiryFollowUp> followUps = followUpService.getFollowUpsByEnquiry(id);
            model.addAttribute("followUps", followUps);
            
            model.addAttribute("courses", courseService.getAllCourses());
            
            return "crm/enquiry-detail";
        }
        return "redirect:/enquiry";
    }

    // Add follow-up for enquiry
    @PostMapping("/enquiry/{id}/followup")
    public String addFollowUp(@PathVariable Long id, @ModelAttribute EnquiryFollowUp followUp,
                              RedirectAttributes redirectAttributes) {
        followUpService.createFollowUp(id, followUp);
        redirectAttributes.addFlashAttribute("message", "Follow-up added successfully!");
        return "redirect:/crm/enquiry/" + id;
    }

    // ==================== ENROLLMENT FROM ENQUIRY ====================

    // Convert enquiry to student enrollment
    @PostMapping("/enroll-from-enquiry/{enquiryId}")
    public String enrollFromEnquiry(@PathVariable Long enquiryId,
                                    @RequestParam Long courseId,
                                    RedirectAttributes redirectAttributes) {
        StudentEnrollment enrollment = enrollmentService.convertEnquiryToEnrollment(enquiryId, courseId);
        if (enrollment != null) {
            redirectAttributes.addFlashAttribute("message", "Student enrolled successfully!");
            return "redirect:/crm/enrollment/" + enrollment.getId();
        }
        redirectAttributes.addFlashAttribute("error", "Enrollment failed!");
        return "redirect:/crm/enquiry/" + enquiryId;
    }

    // ==================== STUDENT ENROLLMENT MANAGEMENT ====================

    // View enrollment details with payment tracking
    @GetMapping("/enrollment/{id}")
    public String viewEnrollment(@PathVariable Long id, Model model) {
        Optional<StudentEnrollment> enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment.isPresent()) {
            model.addAttribute("enrollment", enrollment.get());
            
            Long studentId = enrollment.get().getStudent().getId();
            List<Payment> payments = paymentService.getPaymentsByStudentId(studentId);
            model.addAttribute("payments", payments);
            
            Double totalFees = enrollment.get().getTotalFees();
            Double paidAmount = enrollment.get().getPaidAmount();
            Double progress = totalFees > 0 ? (paidAmount / totalFees) * 100 : 0;
            model.addAttribute("paymentProgress", progress);
            
            return "crm/enrollment-detail";
        }
        return "redirect:/";
    }

    // Update enrollment status
    @PostMapping("/enrollment/{id}/status")
    public String updateEnrollmentStatus(@PathVariable Long id,
                                         @RequestParam String status,
                                         RedirectAttributes redirectAttributes) {
        enrollmentService.updateEnrollmentStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "Enrollment status updated!");
        return "redirect:/crm/enrollment/" + id;
    }

    // Record payment for enrollment
    @PostMapping("/enrollment/{id}/payment")
    public String recordPaymentForEnrollment(@PathVariable Long id,
                                              @RequestParam Double amount,
                                              @RequestParam String method,
                                              RedirectAttributes redirectAttributes) {
        Optional<StudentEnrollment> enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment.isPresent()) {
            enrollmentService.updateEnrollmentPayment(id, amount);
            
            Payment payment = new Payment();
            payment.setStudentId(enrollment.get().getStudent().getId());
            payment.setAmount(amount);
            payment.setPaymentMethod(method);
            payment.setPaymentStatus("Completed");
            paymentService.savePayment(payment);
            
            redirectAttributes.addFlashAttribute("message", "Payment recorded successfully!");
        }
        return "redirect:/crm/enrollment/" + id;
    }

    // ==================== STUDENT VIEW ====================

    // View all enrollments for a student
    @GetMapping("/student/{id}/enrollments")
    public String viewStudentEnrollments(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            
            List<StudentEnrollment> enrollments = enrollmentService.getEnrollmentsByStudent(id);
            model.addAttribute("enrollments", enrollments);
            
            Double totalPaid = enrollments.stream()
                .mapToDouble(e -> e.getPaidAmount() != null ? e.getPaidAmount() : 0)
                .sum();
            Double totalRemaining = enrollments.stream()
                .mapToDouble(e -> e.getRemainingAmount() != null ? e.getRemainingAmount() : 0)
                .sum();
            
            model.addAttribute("totalPaid", totalPaid);
            model.addAttribute("totalRemaining", totalRemaining);
            
            return "crm/student-enrollments";
        }
        return "redirect:/student";
    }

    // ==================== COURSE VIEW ====================

    // View course with enrolled students
    @GetMapping("/course/{id}/enrollments")
    public String viewCourseEnrollments(@PathVariable Long id, Model model) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            model.addAttribute("course", course.get());
            
            List<StudentEnrollment> enrollments = enrollmentService.getEnrollmentsByCourse(id);
            model.addAttribute("enrollments", enrollments);
            
            long activeCount = enrollments.stream()
                .filter(e -> "Active".equals(e.getEnrollmentStatus()))
                .count();
            model.addAttribute("activeEnrollments", activeCount);
            
            double totalCollected = enrollments.stream()
                .mapToDouble(e -> e.getPaidAmount() != null ? e.getPaidAmount() : 0)
                .sum();
            model.addAttribute("totalCollected", totalCollected);
            
            return "crm/course-enrollments";
        }
        return "redirect:/course";
    }

    // ==================== CRM FLOW DASHBOARD ====================

    @GetMapping("/dashboard")
    public String crmFlowDashboard(Model model) {
        long totalEnquiries = enquiryService.getAllEnquiries().size();
        long convertedEnquiries = enquiryService.getEnquiriesByStatus("Converted").size();
        
        List<EnquiryFollowUp> pendingFollowUps = followUpService.getPendingFollowUps();
        
        List<StudentEnrollment> allEnrollments = enrollmentService.getAllEnrollments();
        long activeEnrollments = allEnrollments.stream()
            .filter(e -> "Active".equals(e.getEnrollmentStatus()))
            .count();
        
        double totalRevenue = enrollmentService.getTotalRevenueByEnrollments();
        double collectedRevenue = enrollmentService.getCollectedRevenueByEnrollments();
        double pendingRevenue = enrollmentService.getPendingRevenueByEnrollments();
        
        model.addAttribute("totalEnquiries", totalEnquiries);
        model.addAttribute("convertedEnquiries", convertedEnquiries);
        model.addAttribute("conversionRate", totalEnquiries > 0 ? 
            String.format("%.2f", (convertedEnquiries * 100.0) / totalEnquiries) : "0");
        
        model.addAttribute("pendingFollowUps", pendingFollowUps.size());
        
        model.addAttribute("activeEnrollments", activeEnrollments);
        model.addAttribute("totalEnrollments", allEnrollments.size());
        
        model.addAttribute("totalRevenue", String.format("%.2f", totalRevenue));
        model.addAttribute("collectedRevenue", String.format("%.2f", collectedRevenue));
        model.addAttribute("pendingRevenue", String.format("%.2f", pendingRevenue));
        
        model.addAttribute("topEnrollments", allEnrollments.stream()
            .sorted((a, b) -> b.getEnrollmentDate().compareTo(a.getEnrollmentDate()))
            .limit(5)
            .toList()
        );
        
        return "crm/dashboard";
    }
    @GetMapping("/enquiry/{id}/followup")
public String handleFollowupGet(@PathVariable Long id) {
    return "redirect:/crm/enquiry/" + id;
}
}