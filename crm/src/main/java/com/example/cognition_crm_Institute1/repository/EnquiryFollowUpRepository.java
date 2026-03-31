package com.example.cognition_crm_Institute1.repository;

import com.example.cognition_crm_Institute1.entity.EnquiryFollowUp;
import com.example.cognition_crm_Institute1.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnquiryFollowUpRepository extends JpaRepository<EnquiryFollowUp, Long> {
    
    List<EnquiryFollowUp> findByEnquiry(Enquiry enquiry);
    
    List<EnquiryFollowUp> findByEnquiryOrderByFollowupDateDesc(Enquiry enquiry);
    
    List<EnquiryFollowUp> findByFollowupDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<EnquiryFollowUp> findByOutcome(String outcome);
}