-- ============================================================
-- DevCareer OS - Database Setup
-- ============================================================
-- Run this once in MySQL (Workbench, CLI, or DBeaver) BEFORE
-- starting the Spring Boot backend for the first time.
--
-- You do NOT need to create the tables yourself - Hibernate
-- will create them automatically the first time the backend
-- starts (because spring.jpa.hibernate.ddl-auto=update).
-- This script only creates the empty database + a dedicated
-- MySQL user, which is good practice.
-- ============================================================

CREATE DATABASE IF NOT EXISTS devcareer_os
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- OPTIONAL: create a dedicated MySQL user instead of using root.
-- If you do this, update backend/src/main/resources/application.properties
-- with these same credentials.
--
-- CREATE USER IF NOT EXISTS 'devcareer_user'@'localhost' IDENTIFIED BY 'devcareer_pass';
-- GRANT ALL PRIVILEGES ON devcareer_os.* TO 'devcareer_user'@'localhost';
-- FLUSH PRIVILEGES;

USE devcareer_os;

-- That's it! Start the backend now (see README.md) and Hibernate will
-- create all the tables for you:
--   users, skills, dsa_problems, learning_topics, projects,
--   certifications, job_applications, interviews
