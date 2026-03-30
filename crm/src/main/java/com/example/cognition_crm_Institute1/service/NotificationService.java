package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Notification;
import com.example.cognition_crm_Institute1.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    public void notifyDashboard(String title, String message) {

        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReadStatus(false);

        notificationRepository.save(notification);

        messagingTemplate.convertAndSend("/topic/dashboard", notification);
    }
}