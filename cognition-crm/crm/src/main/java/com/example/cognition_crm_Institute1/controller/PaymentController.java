package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.Payment;
import com.example.cognition_crm_Institute1.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Show all payments
    @GetMapping
    public String list(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/list";
    }

    // Add form
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("payment", new Payment());
        return "payment/add";
    }

    // Save payment
    @PostMapping("/save")
    public String save(@ModelAttribute Payment payment) {
        paymentService.savePayment(payment);
        return "redirect:/payment";
    }

    // View pending payments
    @GetMapping("/pending")
    public String viewPending(Model model) {
        List<Payment> pendingPayments = paymentService.getPendingPayments();
        model.addAttribute("payments", pendingPayments);
        return "payment/list";
    }

    // Update payment status
    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        paymentService.updatePaymentStatus(id, status);
        return "redirect:/payment";
    }

    // Delete payment
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payment";
    }
}