package com.example.cognition_crm_Institute1.controller;

import com.example.cognition_crm_Institute1.entity.Notification;
import com.example.cognition_crm_Institute1.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    // Get all notifications
    @GetMapping("/notifications")
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    // Mark as read
    @PostMapping("/notifications/read/{id}")
    public void markAsRead(@PathVariable Long id) {
        Notification n = notificationRepository.findById(id).orElse(null);
        if (n != null) {
            n.setReadStatus(true);
            notificationRepository.save(n);
        }
    }
}