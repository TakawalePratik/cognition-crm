package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.StudentEnrollment;
import com.example.cognition_crm_Institute1.entity.Student;
import com.example.cognition_crm_Institute1.entity.Course;
import com.example.cognition_crm_Institute1.entity.Enquiry;
import com.example.cognition_crm_Institute1.repository.StudentEnrollmentRepository;
import com.example.cognition_crm_Institute1.repository.StudentRepository;
import com.example.cognition_crm_Institute1.repository.CourseRepository;
import com.example.cognition_crm_Institute1.repository.EnquiryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentEnrollmentService {

    @Autowired
    private StudentEnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private NotificationService notificationService;

    // Convert enquiry to enrollment
    public StudentEnrollment convertEnquiryToEnrollment(Long enquiryId, Long courseId) {
        Optional<Enquiry> enquiry = enquiryRepository.findById(enquiryId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (enquiry.isPresent() && course.isPresent()) {
            // Check if student already exists, if not create new
            Student student = studentRepository.findByEmail(enquiry.get().getEmail())
                .orElseGet(() -> {
                    Student newStudent = new Student();
                    newStudent.setName(enquiry.get().getName());
                    newStudent.setEmail(enquiry.get().getEmail());
                    newStudent.setPhone(enquiry.get().getPhone());
                    newStudent.setStudentStatus("Active");
                    return studentRepository.save(newStudent);
                });

            // Check if enrollment already exists
            Optional<StudentEnrollment> existing = 
                enrollmentRepository.findByStudentAndCourse(student, course.get());
            
            if (existing.isPresent()) {
                return existing.get();
            }

            // Create new enrollment
            StudentEnrollment enrollment = new StudentEnrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course.get());
            enrollment.setEnquiry(enquiry.get());
            enrollment.setTotalFees(course.get().getCourseFees());
            enrollment.setRemainingAmount(course.get().getCourseFees());
            enrollment.setPaidAmount(0.0);
            
            // Set expected completion date
            if (course.get().getDurationMonths() != null) {
                enrollment.setExpectedCompletionDate(
                    LocalDate.now().plusMonths(course.get().getDurationMonths())
                );
            }

            StudentEnrollment saved = enrollmentRepository.save(enrollment);

            // Update enquiry status
            enquiry.get().setEnquiryStatus("Converted");
            enquiryRepository.save(enquiry.get());

            // Send real-time notification
            notificationService.notifyEnrollment(
                "New enrollment: " + student.getName() + " for " + course.get().getCourseName(),
                student.getId(),
                course.get().getId()
            );

            return saved;
        }
        return null;
    }

    public List<StudentEnrollment> getEnrollmentsByStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            return enrollmentRepository.findByStudentOrderByEnrollmentDateDesc(student.get());
        }
        return List.of();
    }

    public List<StudentEnrollment> getEnrollmentsByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return enrollmentRepository.findByCourse(course.get());
        }
        return List.of();
    }

    public Optional<StudentEnrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    // Update enrollment with payment tracking
    public StudentEnrollment updateEnrollmentPayment(Long enrollmentId, Double paymentAmount) {
        Optional<StudentEnrollment> enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment.isPresent()) {
            StudentEnrollment existing = enrollment.get();
            existing.setPaidAmount(existing.getPaidAmount() + paymentAmount);
            existing.setRemainingAmount(existing.getTotalFees() - existing.getPaidAmount());
            existing.setLastUpdated(LocalDateTime.now());
            return enrollmentRepository.save(existing);
        }
        return null;
    }

    public StudentEnrollment updateEnrollmentStatus(Long enrollmentId, String status) {
        Optional<StudentEnrollment> enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment.isPresent()) {
            StudentEnrollment existing = enrollment.get();
            existing.setEnrollmentStatus(status);
            existing.setLastUpdated(LocalDateTime.now());
            
            if ("Completed".equals(status)) {
                existing.setActualCompletionDate(LocalDate.now());
            }
            
            StudentEnrollment saved = enrollmentRepository.save(existing);
            
            notificationService.notifyDashboard(
                "Enrollment Status Changed",
                existing.getStudent().getName() + " - " + status
            );
            
            return saved;
        }
        return null;
    }

    public List<StudentEnrollment> getActiveEnrollments() {
        return enrollmentRepository.findByEnrollmentStatus("Active");
    }

    public List<StudentEnrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    public long getEnrollmentCountByCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isPresent()) {
            return enrollmentRepository.countByCourse(course.get());
        }
        return 0;
    }

    public double getTotalRevenueByEnrollments() {
        return enrollmentRepository.findAll().stream()
            .mapToDouble(e -> e.getTotalFees() != null ? e.getTotalFees() : 0)
            .sum();
    }

    public double getCollectedRevenueByEnrollments() {
        return enrollmentRepository.findAll().stream()
            .mapToDouble(e -> e.getPaidAmount() != null ? e.getPaidAmount() : 0)
            .sum();
    }

    public double getPendingRevenueByEnrollments() {
        return enrollmentRepository.findAll().stream()
            .mapToDouble(e -> e.getRemainingAmount() != null ? e.getRemainingAmount() : 0)
            .sum();
    }
}