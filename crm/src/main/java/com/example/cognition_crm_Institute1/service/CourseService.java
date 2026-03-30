package com.example.cognition_crm_Institute1.service;

import com.example.cognition_crm_Institute1.entity.Course;
import com.example.cognition_crm_Institute1.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // Save course
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    // Update course
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Course c = course.get();
            c.setCourseName(courseDetails.getCourseName());
            c.setDurationMonths(courseDetails.getDurationMonths());
            c.setCourseFees(courseDetails.getCourseFees());
            c.setCapacity(courseDetails.getCapacity());
            c.setDescription(courseDetails.getDescription());
            c.setIsActive(courseDetails.getIsActive());
            return courseRepository.save(c);
        }
        return null;
    }

    // Delete course
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // Get active courses
    public List<Course> getActiveCourses() {
        return courseRepository.findAll().stream()
                .filter(Course::getIsActive)
                .toList();
    }
}