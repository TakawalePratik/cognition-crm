package com.example.cognition_crm_Institute1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ==================== ENQUIRY NOTIFICATIONS ====================
    
    public void notifyNewEnquiry(String message) {
        messagingTemplate.convertAndSend("/topic/enquiries", 
            new Notification("New Enquiry", message, LocalDateTime.now(), "enquiry"));
    }

    public void notifyFollowUp(String title, String message, Long enquiryId) {
        Map<String, Object> data = new HashMap<>();
        data.put("enquiryId", enquiryId);
        
        messagingTemplate.convertAndSend("/topic/followups", 
            createNotificationWithData(title, message, "followup", data));
    }

    // ==================== STUDENT NOTIFICATIONS ====================
    
    public void notifyNewStudent(String message) {
        messagingTemplate.convertAndSend("/topic/students", 
            new Notification("New Student", message, LocalDateTime.now(), "student"));
    }

    public void notifyEnrollment(String message, Long studentId, Long courseId) {
        Map<String, Object> data = new HashMap<>();
        data.put("studentId", studentId);
        data.put("courseId", courseId);
        
        messagingTemplate.convertAndSend("/topic/enrollments", 
            createNotificationWithData("New Enrollment", message, "enrollment", data));
    }

    // ==================== PAYMENT NOTIFICATIONS ====================
    
    public void notifyPayment(String message) {
        messagingTemplate.convertAndSend("/topic/payments", 
            new Notification("Payment Received", message, LocalDateTime.now(), "payment"));
    }

    public void notifyPaymentReminder(Long enrollmentId, Double amount) {
        Map<String, Object> data = new HashMap<>();
        data.put("enrollmentId", enrollmentId);
        data.put("amount", amount);
        
        messagingTemplate.convertAndSend("/topic/payment-reminders", 
            createNotificationWithData("Payment Due", "Amount: ₹" + amount, "reminder", data));
    }

    // ==================== DASHBOARD NOTIFICATIONS ====================
    
    public void notifyDashboard(String title, String message) {
        messagingTemplate.convertAndSend("/topic/dashboard", 
            new Notification(title, message, LocalDateTime.now(), "dashboard"));
    }

    // ==================== HELPER METHOD ====================
    
    private Map<String, Object> createNotificationWithData(String title, String message, String type, Map<String, Object> data) {
        Map<String, Object> notificationMap = new HashMap<>();
        notificationMap.put("title", title);
        notificationMap.put("message", message);
        notificationMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        notificationMap.put("type", type);
        notificationMap.put("data", data);
        return notificationMap;
    }

    // ==================== NOTIFICATION CLASS ====================
    
    public static class Notification {
        public String title;
        public String message;
        public String timestamp;
        public String type;

        public Notification(String title, String message, LocalDateTime time, String type) {
            this.title = title;
            this.message = message;
            this.timestamp = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            this.type = type;
        }

        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public String getTimestamp() { return timestamp; }
        public String getType() { return type; }
    }
}