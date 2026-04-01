package com.example.cognition_crm_Institute1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query("SELECT e.enquiryStatus, COUNT(e) FROM Enquiry e GROUP BY e.enquiryStatus")
List<Object[]> countByStatus();

@Query("SELECT DATE(e.createdDate), COUNT(e) FROM Enquiry e GROUP BY DATE(e.createdDate)")
List<Object[]> countByDate();

@Query("SELECT e.courseInterest, COUNT(e) FROM Enquiry e GROUP BY e.courseInterest")
List<Object[]> countByCourse();

@Query("SELECT COUNT(e) FROM Enquiry e")
Long totalEnquiries();

@Query("SELECT COUNT(e) FROM Enquiry e WHERE e.enquiryStatus = 'Converted'")
Long convertedEnquiries();
}