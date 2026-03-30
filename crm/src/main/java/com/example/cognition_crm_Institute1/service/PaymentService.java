package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Payment;
import com.example.cognition_crm_Institute1.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment savePayment(Payment payment) {

        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment saved = paymentRepository.save(payment);

        notificationService.notifyDashboard(
            "Payment Received",
            "₹" + saved.getAmount() + " payment received"
        );

        return saved;
    }

    public List<Payment> getPaymentsByStudentId(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public List<Payment> getPendingPayments() {
        return paymentRepository.findByPaymentStatus("Pending");
    }

    public Payment updatePaymentStatus(Long id, String status) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            Payment p = payment.get();
            p.setPaymentStatus(status);
            return paymentRepository.save(p);
        }
        return null;
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}