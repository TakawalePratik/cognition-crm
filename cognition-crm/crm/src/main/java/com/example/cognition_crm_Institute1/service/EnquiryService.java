package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Enquiry;
import com.example.cognition_crm_Institute1.repository.EnquiryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private NotificationService notificationService;

    public Enquiry saveEnquiry(Enquiry enquiry) {

        Enquiry saved = enquiryRepository.save(enquiry);

        notificationService.notifyDashboard(
            "New Enquiry",
            saved.getName() + " interested in " + saved.getCourseInterest()
        );

        return saved;
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    public Optional<Enquiry> getEnquiryById(Long id) {
        return enquiryRepository.findById(id);
    }

    public void deleteEnquiry(Long id) {
        enquiryRepository.deleteById(id);
    }

    public Enquiry updateEnquiry(Long id, Enquiry enquiry) {
        enquiry.setId(id);
        return enquiryRepository.save(enquiry);
    }

    public Enquiry updateStatus(Long id, String status, String remarks) {
        Optional<Enquiry> enquiry = enquiryRepository.findById(id);
        if (enquiry.isPresent()) {
            Enquiry existing = enquiry.get();
            existing.setEnquiryStatus(status);
            existing.setRemarks(remarks);
            existing.setLastFollowUp(LocalDateTime.now());
            return enquiryRepository.save(existing);
        }
        return null;
    }

    public List<Enquiry> getEnquiriesByStatus(String status) {
        return enquiryRepository.findByEnquiryStatus(status);
    }
}