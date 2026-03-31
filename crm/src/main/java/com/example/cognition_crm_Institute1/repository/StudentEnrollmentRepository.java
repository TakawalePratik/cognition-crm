package com.example.cognition_crm_Institute1.repository;

import com.example.cognition_crm_Institute1.entity.StudentEnrollment;
import com.example.cognition_crm_Institute1.entity.Student;
import com.example.cognition_crm_Institute1.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    
    List<StudentEnrollment> findByStudent(Student student);
    
    List<StudentEnrollment> findByCourse(Course course);
    
    List<StudentEnrollment> findByEnrollmentStatus(String status);
    
    Optional<StudentEnrollment> findByStudentAndCourse(Student student, Course course);
    
    List<StudentEnrollment> findByStudentOrderByEnrollmentDateDesc(Student student);
    
    long countByEnrollmentStatus(String status);
    
    long countByCourse(Course course);
}