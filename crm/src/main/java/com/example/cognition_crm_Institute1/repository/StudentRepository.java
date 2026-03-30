package com.example.cognition_crm_Institute1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cognition_crm_Institute1.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    List<Student> findByStudentStatus(String studentStatus);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByCourseEnrolled(String courseEnrolled);
}