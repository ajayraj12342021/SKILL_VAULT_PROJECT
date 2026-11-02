CREATE DATABASE skill_vault;
USE skill_vault;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    college VARCHAR(100),
    branch VARCHAR(50),
    semester INT
);

CREATE TABLE skills (
    skill_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    skill_name VARCHAR(50) NOT NULL,
    proficiency VARCHAR(20),
    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE
);

CREATE TABLE certificates (
    certificate_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    issuer VARCHAR(80),
    completion_date DATE,
    certificate_url VARCHAR(255),
    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE
);

CREATE TABLE projects (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    technologies VARCHAR(150),
    github_url VARCHAR(255),
    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE
);
