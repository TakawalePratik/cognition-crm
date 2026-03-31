package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Enquiry;
import com.example.cognition_crm_Institute1.entity.EnquiryFollowUp;
import com.example.cognition_crm_Institute1.repository.EnquiryFollowUpRepository;
import com.example.cognition_crm_Institute1.repository.EnquiryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryFollowUpService {

    @Autowired
    private EnquiryFollowUpRepository followUpRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private NotificationService notificationService;

    // Create follow-up and auto-update enquiry status
    public EnquiryFollowUp createFollowUp(Long enquiryId, EnquiryFollowUp followUp) {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        if (enquiry.isPresent()) {
            followUp.setEnquiry(enquiry.get());
            
            // Auto-update enquiry status based on follow-up outcome
            if ("Positive".equals(followUp.getOutcome())) {
                enquiry.get().setEnquiryStatus("Qualified");
            } else if ("Negative".equals(followUp.getOutcome())) {
                enquiry.get().setEnquiryStatus("Rejected");
            } else {
                enquiry.get().setEnquiryStatus("Contacted");
            }
            
            enquiry.get().setLastFollowUp(LocalDateTime.now());
            enquiryRepository.save(enquiry.get());
            
            EnquiryFollowUp saved = followUpRepository.save(followUp);
            
            // Send real-time notification
            notificationService.notifyFollowUp(
                "Follow-up logged for " + enquiry.get().getName(),
                followUp.getNotes(),
                enquiryId
            );
            
            return saved;
        }
        return null;
    }

    public List<EnquiryFollowUp> getFollowUpsByEnquiry(Long enquiryId) {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        if (enquiry.isPresent()) {
            return followUpRepository.findByEnquiryOrderByFollowupDateDesc(enquiry.get());
        }
        return List.of();
    }

    public Optional<EnquiryFollowUp> getFollowUpById(Long id) {
        return followUpRepository.findById(id);
    }

    public EnquiryFollowUp updateFollowUp(Long id, EnquiryFollowUp followUpDetails) {
        Optional<EnquiryFollowUp> followUp = followUpRepository.findById(id);
        if (followUp.isPresent()) {
            EnquiryFollowUp existing = followUp.get();
            existing.setCommunicationMode(followUpDetails.getCommunicationMode());
            existing.setNotes(followUpDetails.getNotes());
            existing.setOutcome(followUpDetails.getOutcome());
            existing.setNextFollowupDate(followUpDetails.getNextFollowupDate());
            return followUpRepository.save(existing);
        }
        return null;
    }

    public void deleteFollowUp(Long id) {
        followUpRepository.deleteById(id);
    }

    public List<EnquiryFollowUp> getPendingFollowUps() {
        return followUpRepository.findAll().stream()
            .filter(f -> f.getNextFollowupDate() != null && 
                         f.getNextFollowupDate().isBefore(LocalDateTime.now().plusDays(1)))
            .toList();
    }
}