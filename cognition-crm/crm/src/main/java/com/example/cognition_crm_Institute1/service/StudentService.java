package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Student;
import com.example.cognition_crm_Institute1.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private NotificationService notificationService;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student saveStudent(Student student) {

        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDateTime.now());
        }

        Student saved = studentRepository.save(student);

        notificationService.notifyDashboard(
            "New Admission",
            "Student " + saved.getName() + " enrolled"
        );

        return saved;
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student s = student.get();
            s.setName(studentDetails.getName());
            s.setEmail(studentDetails.getEmail());
            s.setPhone(studentDetails.getPhone());
            s.setDateOfBirth(studentDetails.getDateOfBirth());
            s.setCourseEnrolled(studentDetails.getCourseEnrolled());
            s.setStudentStatus(studentDetails.getStudentStatus());
            s.setGuardianName(studentDetails.getGuardianName());
            s.setGuardianPhone(studentDetails.getGuardianPhone());
            s.setAddress(studentDetails.getAddress());
            return studentRepository.save(s);
        }
        return null;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> getStudentsByStatus(String status) {
        return studentRepository.findByStudentStatus(status);
    }

    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourseEnrolled(course);
    }
}