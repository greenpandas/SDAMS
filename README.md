# Self Defence Academy Management System (SDAMS)

## Project Overview

The Self Defence Academy Management System (SDAMS) is a Java-based desktop application developed using Object-Oriented Programming (OOP) principles. The system is designed to automate and manage the daily operations of a self-defence academy through a centralized platform that supports multiple user roles and academy management functions.

The project provides authentication, role-based access control, student management, staff management, scheduling, attendance tracking, booking management, payment tracking, equipment management, issue reporting, and reporting capabilities.

The application is currently developed using Java, NetBeans, and file-based data persistence. Migration to a Java Swing graphical user interface is currently in progress.

---

# Purpose of the System

Many self-defence academies still rely on paper records or spreadsheet-based management systems. These approaches often lead to:

- Duplicate records
- Missing information
- Attendance tracking issues
- Inefficient payment management
- Limited reporting capabilities
- Poor operational visibility

SDAMS aims to solve these challenges by providing a centralized digital management system that supports all major academy operations.

---

# Project Objectives

The primary objectives of SDAMS are:

- Provide secure authentication and role-based authorization.
- Manage student and staff records efficiently.
- Digitally record attendance information.
- Support schedule management and class bookings.
- Manage academy equipment and facilities.
- Record and track payment information.
- Generate operational and revenue reports.
- Demonstrate Object-Oriented Programming concepts through real-world implementation.

---

# Technology Stack

| Component | Technology |
|------------|------------|
| Programming Language | Java |
| Development Environment | NetBeans |
| Version Control | Git |
| Repository Hosting | GitHub |
| Data Storage | Text Files |
| User Interface | Java Swing (In Progress) |
| Development Methodology | Incremental Development |

---

# User Roles

The system currently supports six user roles.

## Manager

Responsibilities:

- Manage Administrators
- View Staff Information
- Generate Staff Reports
- Generate Revenue Reports

---

## Administrator

Responsibilities:

- Manage Students
- Manage Staff
- Manage Payments

---

## Instructor

Responsibilities:

- Create Class Schedules
- View Class Schedules
- Report Facility Issues
- View Student Ratings

---

## Student

Responsibilities:

- View Available Classes
- Book Classes
- View Bookings
- View Payment Records
- Submit Ratings

---

## Attendance Officer

Responsibilities:

- Record Attendance
- View Attendance Records

---

## Facilities Officer

Responsibilities:

- Add Equipment
- View Equipment
- View Reported Facility Issues

---

# System Architecture

The system follows a layered architecture design.

```text
GUI Layer (Swing Forms)
          ↓
      Menu Layer
          ↓
     Service Layer
          ↓
      FileManager
          ↓
      Data Files
```

Each layer has a dedicated responsibility to improve maintainability and modularity.

---

# Authentication Flow

User authentication is performed using data stored in `users.txt`.

```text
users.txt
      ↓
FileManager
      ↓
UserService
      ↓
Credential Validation
      ↓
Role Detection
      ↓
MenuRouter
      ↓
Role-Specific Dashboard
```

Successful authentication automatically routes users to their appropriate module.

---

# Object-Oriented Programming Concepts

## Encapsulation

System data is protected through private attributes and accessed using getters and setters.

Example:

```java
private String username;

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}
```

---

## Inheritance

The User class acts as the parent class for all system users.

```text
User
├── Manager
├── Administrator
├── Instructor
├── Student
├── FacilitiesOfficer
└── AttendanceOfficer
```

This structure promotes code reuse and consistency across user roles.

---

## Abstraction

Business logic is separated into dedicated service classes.

```text
UserService
ManagerService
AdminService
StudentService
InstructorService
AttendanceService
FacilitiesService
PaymentService
```

This separates business logic from the user interface layer.

---

## Polymorphism

The inheritance hierarchy is designed to support future polymorphic behavior among user roles as the system evolves.

---

# Project Structure

```text
SDAMS
│
├── data
│   ├── attendance.txt
│   ├── bookings.txt
│   ├── classes.txt
│   ├── equipment.txt
│   ├── issues.txt
│   ├── payments.txt
│   ├── ratings.txt
│   └── users.txt
│
├── docs
├── diagrams
├── screenshots
│
├── src
│   ├── model
│   ├── gui
│   ├── service
│   ├── filehandling
│   └── reports
│
└── nbproject
```

---

# Data Storage Design

The project currently uses text-file persistence rather than a relational database.

## Data Files

| File | Purpose |
|--------|---------|
| users.txt | User accounts, authentication and role information |
| classes.txt | Instructor-created class schedules |
| bookings.txt | Student class bookings |
| attendance.txt | Attendance records |
| payments.txt | Payment records |
| equipment.txt | Equipment inventory |
| issues.txt | Facility issues |
| ratings.txt | Student ratings and feedback |

---

# Implemented Modules

## Core System

### Authentication and User Management

✅ User Authentication

✅ Login Validation

✅ Role Detection

✅ Role-Based Access Control

✅ Menu Routing

✅ File-Based Persistence

✅ User Management Foundation

---

## Manager Module

### Administrator Management (CRUD)

✅ Add Administrator

✅ View Administrators

✅ Search Administrator

✅ Update Administrator

✅ Delete Administrator

### Staff Management

✅ View Staff Information

✅ Staff Reporting

### Reporting

✅ Revenue Reporting

---

## Administrator Module

### Student Management (CRUD)

✅ Add Student

✅ View Students

✅ Search Student

✅ Update Student

✅ Delete Student

### Staff Management

✅ View Staff

✅ Search Staff

### Payment Management

✅ Add Payment Records

✅ View Payment Records

---

## Student Module

✅ View Classes

✅ Book Classes

✅ View Bookings

✅ View Payment History

✅ Submit Ratings

---

## Instructor Module

✅ Create Schedule

✅ View Schedule

✅ Report Facility Issues

✅ View Student Ratings

---

## Attendance Module

✅ Record Attendance

✅ View Attendance

✅ Attendance Persistence

---

## Facilities Module

✅ Add Equipment

✅ View Equipment

✅ View Facility Issues

---

# Implemented Workflows

## Class Scheduling Workflow

```text
Instructor
      ↓
Create Schedule
      ↓
classes.txt
      ↓
Student
      ↓
View Available Classes
```

---

## Class Booking Workflow

```text
Student
      ↓
View Classes
      ↓
Book Class
      ↓
bookings.txt
```

---

## Attendance Workflow

```text
Attendance Officer
      ↓
Record Attendance
      ↓
attendance.txt
      ↓
View Attendance
```

---

## Facility Issue Reporting Workflow

```text
Instructor
      ↓
Report Issue
      ↓
issues.txt
      ↓
Facilities Officer
      ↓
View Reported Issues
```

---

## Payment Workflow

```text
Administrator
      ↓
Add Payment
      ↓
payments.txt
      ↓
Student
      ↓
View Payments
      ↓
Manager
      ↓
Generate Revenue Reports
```

---

# Service Layer

The service layer contains the core business logic of the system.

## Implemented Services

```text
UserService
ManagerService
AdminService
StudentService
InstructorService
AttendanceService
FacilitiesService
PaymentService
FileManager
```

---

# GUI Development Status

## Completed

✅ LoginForm

✅ Role-Based GUI Navigation

✅ Manager Dashboard Foundation

✅ Dashboard Structure Creation

---

## In Progress

🔄 Administrator Dashboard

🔄 Student Dashboard

🔄 Instructor Dashboard

🔄 Attendance Dashboard

🔄 Facilities Dashboard

🔄 Backend-to-GUI Integration

---

# Current Project Status

## Completed

✅ Authentication System

✅ File Storage Layer

✅ User Management

✅ Manager Module

✅ Administrator Module

✅ Student CRUD Operations

✅ Scheduling System

✅ Booking System

✅ Attendance Tracking

✅ Equipment Management

✅ Payment Management

✅ Revenue Reporting

✅ Facility Issue Tracking

✅ Rating System

---

## Currently In Progress

🔄 Java Swing GUI Development

🔄 UML Diagram Creation

🔄 Integration Testing

🔄 Documentation Expansion

---

## Remaining Work

⏳ Complete GUI Migration

⏳ Advanced Reports

⏳ System Testing

⏳ Bug Fixing

⏳ Documentation Finalization

⏳ Presentation Preparation

⏳ Final Submission

---

# Development Progress

| Phase | Status |
|---------|---------|
| Planning & Design | ✅ Complete |
| Authentication | ✅ Complete |
| Core Backend Development | ✅ Complete |
| Manager Module | ✅ Complete |
| Administrator Module | ✅ Complete |
| Student Management | ✅ Complete |
| Attendance Module | ✅ Complete |
| Facilities Module | ✅ Complete |
| Payment Module | ✅ Complete |
| GUI Development | 🔄 In Progress |
| Testing | ⏳ Pending |
| Documentation | 🔄 In Progress |

---

# Estimated Completion

```text
Backend Development      : 90%
GUI Development          : 20%
Documentation            : 30%

Overall Project Progress : 75% - 80%
```

---

# Team Workflow

Before starting development:

```bash
git pull
```

After completing a feature:

```bash
git add .
git commit -m "Meaningful Feature Description"
git push
```

Example:

```bash
git commit -m "Implement instructor scheduling workflow"
```

---

# Upcoming Milestones

## Week 5

- Complete GUI Development
- Connect GUI to Backend Services
- UML Diagram Completion
- Begin Integration Testing

## Week 6

- Complete Documentation
- Perform System Testing
- Fix Remaining Bugs
- Prepare Presentation
- Final Submission

---

# Contributors

**SDAMS Development Team**

Academic Project - Object-Oriented Programming (OOP)

Self Defence Academy Management System (SDAMS)
