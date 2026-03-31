create a schema named <b>cognition_crm</b> in your mysql and add your username <br> and password to application.properties in src/main/resourses/application.properties

<br><br>


-- =========================
-- COURSES
-- =========================
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL UNIQUE,
    duration_months INT,
    course_fees DOUBLE,
    capacity INT,
    description VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE
);

INSERT INTO courses (course_name, duration_months, course_fees, capacity, description)
VALUES 
('Java Full Stack', 6, 50000, 50, 'Complete Java + Spring Boot + React'),
('Python Data Science', 5, 45000, 40, 'Python with ML and Data Science'),
('Web Development', 4, 30000, 60, 'HTML, CSS, JS, React');


-- =========================
-- ENQUIRIES
-- =========================
CREATE TABLE enquiries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    course_interest VARCHAR(255),
    enquiry_status VARCHAR(50) DEFAULT 'New',
    remarks VARCHAR(500),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_follow_up DATETIME,
    assigned_to VARCHAR(255)
);

INSERT INTO enquiries (name, email, phone, course_interest, assigned_to)
VALUES 
('Rahul Patil', 'rahul@gmail.com', '9876543210', 'Java Full Stack', 'Admin'),
('Sneha Joshi', 'sneha@gmail.com', '9123456780', 'Python Data Science', 'Counsellor');


-- =========================
-- NOTIFICATIONS
-- =========================
CREATE TABLE notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    message TEXT,
    read_status BOOLEAN DEFAULT FALSE,
    created_at DATETIME
);

INSERT INTO notification (title, message, created_at)
VALUES 
('New Enquiry', 'New student enquiry received', NOW()),
('Payment Received', 'Student paid course fees', NOW());


-- =========================
-- PAYMENTS
-- =========================
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT,
    amount DOUBLE NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) DEFAULT 'Pending',
    payment_method VARCHAR(50),
    transaction_id VARCHAR(255),
    remarks VARCHAR(500)
);

INSERT INTO payments (student_id, amount, payment_status, payment_method, transaction_id)
VALUES 
(1, 20000, 'Completed', 'Cash', 'TXN12345'),
(2, 15000, 'Pending', 'Online', 'TXN67890');


-- =========================
-- ROLES
-- =========================
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

INSERT INTO roles (name)
VALUES 
('ADMIN'),
('USER'),
('COUNSELLOR');


-- =========================
-- STUDENTS
-- =========================
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    date_of_birth DATE,
    enrollment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    course_enrolled VARCHAR(255),
    student_status VARCHAR(50) DEFAULT 'Active',
    guardian_name VARCHAR(255),
    guardian_phone VARCHAR(20),
    address TEXT
);

INSERT INTO students (name, email, phone, course_enrolled, guardian_name)
VALUES 
('Amit Sharma', 'amit@gmail.com', '9999999999', 'Java Full Stack', 'Ramesh Sharma'),
('Priya Singh', 'priya@gmail.com', '8888888888', 'Python Data Science', 'Suresh Singh');


-- =========================
-- USERS
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    enabled BOOLEAN DEFAULT TRUE,
    role VARCHAR(50) DEFAULT 'USER'
);

INSERT INTO users (username, password, email, full_name, role)
VALUES 
('admin', 'admin123', 'admin@crm.com', 'System Admin', 'ADMIN'),
('user1', 'user123', 'user1@crm.com', 'Normal User', 'USER');
