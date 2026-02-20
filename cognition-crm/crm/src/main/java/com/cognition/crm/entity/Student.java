package com.cognition.crm.entity;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "students",uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone"),
        @UniqueConstraint(columnNames = "email")
    }
)


public class Student {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;

    @OneToOne
@JoinColumn(name = "enquiry_id")
private Enquiry enquiries;

@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


@Column(name = "name", nullable = false)
private String name;

@Column(name ="email", unique = true, nullable = false)
private String email;

@Column(name ="phone",  unique = true, nullable = false)
private String phone;

@Column(name="admission_date")  
private Date admission_date;

@Column(name="status")  
private String  status;

public Student(int id,Enquiry enquiries,Course course, String name, String email, String phone,
        Date admission_date, String status) {
    this.id = id;
    this.enquiries = enquiries;
    this.course = course;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.admission_date = admission_date;
    this.status = status;
}



public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public Enquiry getEnquiries() {
    return enquiries;
}

public Course getCourse() {
    return course;
}

public void setCourse(Course course) {
    this.course = course;
}

public void setEnquiries(Enquiry enquiries) {
    this.enquiries = enquiries;
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

public Date getAdmission_date() {
    return admission_date;
}

public void setAdmission_date(Date admission_date) {
    this.admission_date = admission_date;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}



}
