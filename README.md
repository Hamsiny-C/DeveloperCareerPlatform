# 🚀 DevCareer OS

<p align="center">

<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=28&duration=2800&pause=900&color=00E5FF&center=true&vCenter=true&width=760&lines=Developer+Career+Operating+System;Track+%7C+Practice+%7C+Build+%7C+Get+Placement+Ready" alt="DevCareer OS Animated Title" />

</p>

<p align="center">

<img src="https://img.shields.io/badge/Java-21%2F25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
<img src="https://img.shields.io/badge/Spring%20Boot-Backend-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
<img src="https://img.shields.io/badge/React-Frontend-61DAFB?style=for-the-badge&logo=react&logoColor=black" />
<img src="https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
<img src="https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" />

</p>

<p align="center">

<strong>A full-stack Developer Career Operating System built to manage learning, DSA, skills, projects, certifications, job applications and interviews in one platform.</strong>

</p>

<p align="center">

<a href="http://dev-career-os-frontend-579072312142.s3-website.ap-south-1.amazonaws.com/">

<img src="https://img.shields.io/badge/🌐%20LIVE%20DEMO-OPEN%20DEVCAREER%20OS-00C7B7?style=for-the-badge" alt="Live Demo" />

</a>

</p>

---

## ✨ What is DevCareer OS?

**DevCareer OS** is a full-stack platform designed to organize the complete developer placement journey in one place.

Instead of managing DSA practice, skills, learning progress, projects, certifications, job applications and interviews across multiple platforms, DevCareer OS brings everything into a centralized dashboard.

### 🎯 Core Goal

> **Track → Learn → Practice → Build → Apply → Prepare → Get Placement Ready**

---

## 🌐 Live Application

<p align="center">

### 🚀 [Open DevCareer OS](http://dev-career-os-frontend-579072312142.s3-website.ap-south-1.amazonaws.com/)

</p>

<p align="center">

<img src="https://img.shields.io/badge/●%20LIVE-ONLINE-22C55E?style=for-the-badge" />

</p>

**Public URL:**

```text
http://dev-career-os-frontend-579072312142.s3-website.ap-south-1.amazonaws.com/
```

---

## 🧩 Features

| Module               | Features                               |
| -------------------- | -------------------------------------- |
| 🔐 Authentication    | Register, Login, JWT Authentication    |
| 👤 Developer Profile | Manage developer information           |
| 📊 Dashboard         | Centralized placement progress         |
| 💻 Skills            | Track technologies and progress        |
| 🧠 DSA               | Track coding problems and practice     |
| 📚 Learning          | Manage learning topics                 |
| 🚀 Projects          | Track portfolio projects               |
| 🏆 Certifications    | Manage certifications and achievements |
| 💼 Applications      | Track job applications                 |
| 🎤 Interviews        | Manage interview preparation           |
| 🔒 Security          | Spring Security + JWT                  |
| 🗄️ Database         | MySQL + JPA + Hibernate                |

---

## 🏗️ System Architecture

```text
                         🌐 USER
                           │
                           ▼
                ┌─────────────────────┐
                │    React Frontend   │
                │      Amazon S3      │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │ Application Load    │
                │      Balancer       │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │   Spring Boot API   │
                │    ECS Fargate      │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │      MySQL          │
                │     Amazon RDS      │
                └─────────────────────┘

                 Docker → Amazon ECR
```

---

## 🛠️ Technology Stack

### Backend

* ☕ Java
* 🌱 Spring Boot
* 🌐 Spring Web
* 🗃️ Spring Data JPA
* 🔐 Spring Security
* 🎫 JWT
* 📦 Maven

### Frontend

* ⚛️ React
* ⚡ Vite
* 🟨 JavaScript
* 🔗 Axios
* 🎨 HTML5
* 🎨 CSS3

### Database

* 🐬 MySQL
* Hibernate
* JPA

### Cloud & DevOps

* 🐳 Docker
* 📦 Amazon ECR
* 🚀 Amazon ECS Fargate
* 🌐 Application Load Balancer
* 🪣 Amazon S3
* 🗄️ Amazon RDS
* ☁️ AWS VPC

---

## 📁 Project Structure

```text
devcareer-os/
│
├── backend/
│   ├── src/main/java/com/devcareeros/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   │
│   ├── src/main/resources/
│   └── pom.xml
│
├── frontend/
│   └── src/
│       ├── api/
│       ├── components/
│       ├── pages/
│       └── styles/
│
├── database/
│   └── setup.sql
│
└── README.md
```

---

## 🔐 Authentication Flow

```text
                 React Login
                      │
                      ▼
             POST /api/auth/login
                      │
                      ▼
              Spring Security
                      │
                      ▼
                  JWT Token
                      │
                      ▼
              Frontend Storage
                      │
                      ▼
        Authorization: Bearer <token>
                      │
                      ▼
             Protected REST APIs
```

---

## 🔄 Application Data Flow

```text
┌─────────────┐
│   React UI  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    Axios    │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   REST API  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Controller  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Hibernate  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│    MySQL    │
└─────────────┘
```

---

## ⚙️ Required Software

| Tool    | Version                           |
| ------- | --------------------------------- |
| JDK     | 21 or 25                          |
| Maven   | 3.9+                              |
| MySQL   | 8.x                               |
| Node.js | 18+                               |
| IDE     | Spring Tools / IntelliJ / VS Code |

Check installed versions:

```bash
java -version
mvn -version
mysql --version
node -version
npm -version
```

---

## 🗄️ Database Setup

Create the database:

```sql
CREATE DATABASE IF NOT EXISTS devcareer_os
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

Hibernate automatically creates and updates the required tables from the JPA entities.

Configure local database credentials in:

```text
backend/src/main/resources/application.properties
```

For production, use environment variables instead of committing real credentials.

---

## ▶️ Run Locally

### Backend

```bash
cd backend
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

## 🧪 REST API

### Authentication

```text
POST /api/auth/register
POST /api/auth/login
```

### Profile

```text
GET /api/profile
PUT /api/profile
```

### Dashboard

```text
GET /api/dashboard/summary
```

### Skills

```text
GET    /api/skills
POST   /api/skills
PUT    /api/skills/{id}
DELETE /api/skills/{id}
```

### DSA

```text
GET    /api/dsa
POST   /api/dsa
PUT    /api/dsa/{id}
DELETE /api/dsa/{id}
```

### Learning

```text
GET    /api/learning
POST   /api/learning
PUT    /api/learning/{id}
DELETE /api/learning/{id}
```

### Projects

```text
GET    /api/projects
POST   /api/projects
PUT    /api/projects/{id}
DELETE /api/projects/{id}
```

### Certifications

```text
GET    /api/certifications
POST   /api/certifications
PUT    /api/certifications/{id}
DELETE /api/certifications/{id}
```

### Job Applications

```text
GET    /api/applications
POST   /api/applications
PUT    /api/applications/{id}
DELETE /api/applications/{id}
```

### Interviews

```text
GET    /api/interviews
POST   /api/interviews
PUT    /api/interviews/{id}
DELETE /api/interviews/{id}
```

---

## 🧠 Key Concepts Demonstrated

This project demonstrates practical implementation of:

* Object-Oriented Programming
* Dependency Injection
* REST API architecture
* HTTP request/response flow
* DTOs
* JPA
* Hibernate
* Repository Pattern
* Service Layer
* Spring Security
* JWT Authentication
* MySQL Integration
* React Integration
* Docker
* AWS Deployment
* Amazon ECS
* Amazon ECR
* Amazon S3
* Amazon RDS
* Application Load Balancing

---

## 🚀 AWS Deployment

```text
                     AWS CLOUD
                        │
                        ▼
              ┌─────────────────┐
              │   Amazon S3     │
              │ React Frontend  │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │      ALB        │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │  ECS Fargate    │
              │  Spring Boot    │
              └────────┬────────┘
                       │
                       ▼
              ┌─────────────────┐
              │   Amazon RDS    │
              │      MySQL      │
              └─────────────────┘

          Docker → ECR → ECS Fargate
```

---

## 📌 Project Highlights

* 🚀 Full-stack Java application
* 🔐 JWT-based authentication
* 🌐 RESTful backend
* ⚛️ React frontend
* 🗄️ MySQL persistence
* 🐳 Docker containerization
* ☁️ AWS cloud deployment
* 📦 Amazon ECR
* 🚀 Amazon ECS Fargate
* 🪣 Amazon S3
* 🗄️ Amazon RDS
* 🌐 Application Load Balancer
* 📊 Placement-focused dashboard
* 🧠 DSA and learning progress tracking
* 💼 Job application tracking
* 🎤 Interview preparation management

---

## 👨‍💻 Developer

### Hamsiny C

**Java Full Stack Developer**

<p align="center">

<a href="https://github.com/Hamsiny-C">
<img src="https://img.shields.io/badge/GitHub-Hamsiny--C-181717?style=for-the-badge&logo=github" />
</a>

<a href="https://www.linkedin.com/in/hamsiny-c-9b03a2349">
<img src="https://img.shields.io/badge/LinkedIn-Hamsiny%20C-0A66C2?style=for-the-badge&logo=linkedin" />
</a>

<a href="https://leetcode.com/u/GEWCJhefXN/">
<img src="https://img.shields.io/badge/LeetCode-Hamsiny-FFA116?style=for-the-badge&logo=leetcode&logoColor=black" />
</a>

</p>

---

<p align="center">

<a href="http://dev-career-os-frontend-579072312142.s3-website.ap-south-1.amazonaws.com/">

<img src="https://img.shields.io/badge/🚀%20LAUNCH%20DEVCAREER%20OS-00C7B7?style=for-the-badge" />

</a>

</p>

<p align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&color=0:0F172A,50:0EA5E9,100:06B6D4&height=120&section=footer" />

</p>
