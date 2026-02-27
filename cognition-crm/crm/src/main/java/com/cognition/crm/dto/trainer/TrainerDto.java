package com.cognition.crm.dto.trainer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrainerDto {

    private Long trainerId;

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    private String phone;
    private String specialization;

    @NotNull
    private Integer experienceYears;

    private Boolean isActive;

    public Long getTrainerId() {
      return trainerId;
    }

    public void setTrainerId(Long trainerId) {
      this.trainerId = trainerId;
    }

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
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

    public String getSpecialization() {
      return specialization;
    }

    public void setSpecialization(String specialization) {
      this.specialization = specialization;
    }

    public Integer getExperienceYears() {
      return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
      this.experienceYears = experienceYears;
    }

    public Boolean getIsActive() {
      return isActive;
    }

    public void setIsActive(Boolean isActive) {
      this.isActive = isActive;
    }

   
}
