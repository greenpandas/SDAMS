# Self Defence Academy Management System (SDAMS)

## Project Overview

The Self Defence Academy Management System (SDAMS) is a Java-based desktop application developed using Object-Oriented Programming (OOP) principles.

The system is designed to manage the daily operations of a self-defence academy, including:

- User Authentication
- Staff Management
- Student Management
- Class Scheduling
- Attendance Tracking
- Class Booking
- Payment Management
- Equipment Management
- Reporting

---

## Technology Stack

- Java
- NetBeans
- Java Swing (Planned)
- Git & GitHub
- Text File Storage

---

## User Roles

- Manager
- Administrator
- Instructor
- Student
- Facilities Officer
- Attendance Officer

---

## OOP Concepts Implemented

### Encapsulation

- Private attributes
- Getters and Setters

### Inheritance

```text
User
├── Manager
├── Administrator
├── Instructor
├── Student
├── FacilitiesOfficer
└── AttendanceOfficer
```

### Abstraction

- Business logic separated into service classes
- File handling separated into FileManager

### Polymorphism

- Planned during later implementation phases

---

## Features Completed

### Core System

✅ User Model

✅ FileManager

✅ User Authentication

✅ Login Validation

✅ Text File Data Persistence

✅ Role-Based Access Control

✅ Menu Routing System

✅ Console Navigation with Back/Logout Functionality

---

### Manager Module

✅ Administrator CRUD

- Add Administrator
- View Administrators
- Search Administrator
- Update Administrator
- Delete Administrator

✅ View Staff

✅ Generate Staff Report

✅ Revenue Report Foundation

---

### Administrator Module

✅ Student CRUD

- Add Student
- View Students
- Search Student
- Update Student
- Delete Student

✅ Staff Management

- View Staff
- Search Staff

✅ Payment Management Structure

---

### Instructor Module

✅ Instructor Menu

✅ Create Schedule

✅ View Schedule

✅ Schedule Storage using classes.txt

---

## Current System Flow

```text
users.txt
     ↓
FileManager
     ↓
UserService
     ↓
Authentication
     ↓
MenuRouter
     ↓
Role-Based Menus
```

---

## Data Files

```text
users.txt
classes.txt
attendance.txt
bookings.txt
payments.txt
equipment.txt
issues.txt
ratings.txt
students.txt
staff.txt
```

---

## Service Layer

Implemented Services:

```text
UserService
ManagerService
AdminService
StudentService
InstructorService
FileManager
```

---

## Current Project Status

### Completed

✅ Authentication System

✅ User Management Foundation

✅ Role-Based Access Control

✅ Manager Module

✅ Administrator Module

✅ Student CRUD

✅ Staff Management

✅ Instructor Scheduling

✅ File Storage Architecture

---

### In Progress

🔄 Attendance Module

🔄 Facilities Module

🔄 Booking System

🔄 Payment Integration

---

### Upcoming

⏳ Java Swing GUI

⏳ Reporting System

⏳ System Integration

⏳ Testing

⏳ Final Documentation

⏳ Presentation

---

## Team Workflow

Before coding:

```bash
git pull
```

After completing a feature:

```bash
git add .
git commit -m "Feature Description"
git push
```

---

## Development Roadmap

### Week 1
✅ Planning and Setup

✅ GitHub Configuration

✅ Project Structure

✅ UML Planning

---

### Week 2
✅ Authentication System

✅ User Management

✅ Manager Module

✅ Administrator CRUD

---

### Week 3
✅ Student CRUD

✅ Staff Management

✅ Instructor Scheduling

🔄 Attendance Module

🔄 Facilities Module

---

### Week 4
⏳ Booking System

⏳ Attendance Integration

⏳ Payment Integration

⏳ GUI Development

---

### Week 5
⏳ Reporting

⏳ Testing

⏳ Bug Fixing

⏳ GUI Refinement

---

### Week 6
⏳ Documentation

⏳ Presentation

⏳ Final Testing

⏳ Submission
