package com.example.cognition_crm_Institute1.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enquiry_followups")
public class EnquiryFollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enquiry_id", nullable = false)
    private Enquiry enquiry;

    @Column(name = "followup_date", nullable = false)
    private LocalDateTime followupDate = LocalDateTime.now();

    @Column(name = "communication_mode")
    private String communicationMode; // Call, Email, SMS, Meeting

    @Column(length = 500)
    private String notes;

    @Column(name = "outcome")
    private String outcome; // Positive, Neutral, Negative

    @Column(name = "next_followup_date")
    private LocalDateTime nextFollowupDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Enquiry getEnquiry() { return enquiry; }
    public void setEnquiry(Enquiry enquiry) { this.enquiry = enquiry; }

    public LocalDateTime getFollowupDate() { return followupDate; }
    public void setFollowupDate(LocalDateTime followupDate) { this.followupDate = followupDate; }

    public String getCommunicationMode() { return communicationMode; }
    public void setCommunicationMode(String communicationMode) { this.communicationMode = communicationMode; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public LocalDateTime getNextFollowupDate() { return nextFollowupDate; }
    public void setNextFollowupDate(LocalDateTime nextFollowupDate) { this.nextFollowupDate = nextFollowupDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        return "EnquiryFollowUp{" +
                "id=" + id +
                ", enquiryId=" + enquiry.getId() +
                ", followupDate=" + followupDate +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}