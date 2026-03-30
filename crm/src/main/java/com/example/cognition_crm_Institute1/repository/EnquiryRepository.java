package com.example.cognition_crm_Institute1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cognition_crm_Institute1.entity.Enquiry;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
    
    List<Enquiry> findByEnquiryStatus(String enquiryStatus);
    
    List<Enquiry> findByName(String name);
    
    Optional<Enquiry> findByEmail(String email);
    
    List<Enquiry> findByAssignedTo(String assignedTo);
}