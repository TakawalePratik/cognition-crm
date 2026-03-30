package com.example.cognition_crm_Institute1.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "enquiries")
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "course_interest")
    private String courseInterest;

    @Column(name = "enquiry_status")
    private String enquiryStatus = "New"; // New, Contacted, Qualified, Converted, Rejected

    @Column(length = 500)
    private String remarks;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "last_follow_up")
    private LocalDateTime lastFollowUp;

    @Column(name = "assigned_to")
    private String assignedTo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourseInterest() {
        return courseInterest;
    }

    public void setCourseInterest(String courseInterest) {
        this.courseInterest = courseInterest;
    }

    public String getEnquiryStatus() {
        return enquiryStatus;
    }

    public void setEnquiryStatus(String enquiryStatus) {
        this.enquiryStatus = enquiryStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastFollowUp() {
        return lastFollowUp;
    }

    public void setLastFollowUp(LocalDateTime lastFollowUp) {
        this.lastFollowUp = lastFollowUp;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public String toString() {
        return "Enquiry{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", courseInterest='" + courseInterest + '\'' +
                ", enquiryStatus='" + enquiryStatus + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createdDate=" + createdDate +
                ", lastFollowUp=" + lastFollowUp +
                ", assignedTo='" + assignedTo + '\'' +
                '}';
    }
}