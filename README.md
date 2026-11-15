# Self Defence Academy Management System (SDAMS)

## Project Overview

The Self Defence Academy Management System (SDAMS) is a Java-based desktop application developed using Object-Oriented Programming (OOP) principles. The system aims to automate and manage the daily operations of a self-defence academy through a centralized platform that supports multiple user roles and academy management functions.

The application is being developed using Java, NetBeans, file-based storage, and will later include a Java Swing graphical user interface.

---

## Project Goals

The objectives of SDAMS are:

- Provide secure user authentication and role-based access control.
- Manage academy staff and student records efficiently.
- Record attendance digitally.
- Manage class schedules and class bookings.
- Maintain payment records.
- Track facility equipment and reported issues.
- Generate reports for academy management.
- Apply Object-Oriented Programming concepts in a real-world application.

---

## Technology Stack

| Component | Technology |
|------------|------------|
| Programming Language | Java |
| IDE | NetBeans |
| Version Control | Git |
| Repository Hosting | GitHub |
| Data Storage | Text Files |
| GUI Framework | Java Swing (Planned) |

---

## User Roles

The system currently supports the following roles:

### Manager
- Manage Administrators
- View Staff Information
- Generate Staff Reports
- Generate Revenue Reports

### Administrator
- Manage Students
- Manage Staff
- Manage Payments

### Instructor
- Create Class Schedules
- View Class Schedules
- Report Facility Issues
- View Student Ratings

### Student
- View Available Classes
- Book Classes
- View Bookings
- View Payment Records
- Submit Ratings

### Attendance Officer
- Record Attendance
- View Attendance Records

### Facilities Officer
- Add Equipment
- View Equipment
- View Reported Issues

---

## OOP Concepts Implemented

### Encapsulation

Private attributes are accessed through getters and setters.

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

All users inherit common properties and behaviours from the User class.

### Abstraction

Business logic is separated into dedicated service classes:

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

### Polymorphism

The inheritance hierarchy has been designed to support polymorphic behaviour during future development.

---

## System Architecture

The application follows a layered architecture:

```text
User
 ↓
Authentication
 ↓
Role Detection
 ↓
Menu Router
 ↓
Service Layer
 ↓
FileManager
 ↓
Text Files
```

### Authentication Flow

```text
users.txt
     ↓
FileManager
     ↓
UserService
     ↓
Login Validation
     ↓
Role Detection
     ↓
MenuRouter
     ↓
Role Menu
```

---

## Current Project Structure

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

## Data Storage

The system currently uses text-file persistence rather than a database.

### Data Files

| File | Purpose |
|--------|--------|
| users.txt | User accounts and authentication |
| classes.txt | Class schedules |
| bookings.txt | Student bookings |
| attendance.txt | Attendance records |
| payments.txt | Payment records |
| equipment.txt | Equipment records |
| issues.txt | Facility issues |
| ratings.txt | Student ratings |

---

## Implemented Modules

### Core System

✅ User Authentication

✅ Login Validation

✅ Role-Based Access Control

✅ Menu Routing

✅ File Handling Layer

✅ Data Persistence

✅ User Management Foundation

---

### Manager Module

✅ Add Administrator

✅ View Administrators

✅ Search Administrator

✅ Update Administrator

✅ Delete Administrator

✅ View Staff

✅ Generate Staff Report

✅ Generate Revenue Report

---

### Administrator Module

✅ Student CRUD Operations

- Add Student
- View Students
- Search Student
- Update Student
- Delete Student

✅ Manage Staff

- View Staff
- Search Staff

✅ Payment Management

---

### Student Module

✅ View Classes

✅ Book Classes

✅ View Bookings

✅ View Payment Records

✅ Submit Ratings

---

### Instructor Module

✅ Create Schedule

✅ View Schedule

✅ Report Facility Issues

✅ View Ratings

---

### Attendance Module

✅ Record Attendance

✅ View Attendance

✅ Attendance Persistence

---

### Facilities Module

✅ Add Equipment

✅ View Equipment

✅ View Facility Issues

---

## Current System Workflows

### Class Scheduling and Booking

```text
Instructor
      ↓
Create Schedule
      ↓
classes.txt
      ↓
Student
      ↓
View Classes
      ↓
Book Class
      ↓
bookings.txt
```

### Facility Issue Reporting

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

### Payment Management

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
```

---

## Service Layer

Implemented service classes:

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

## Current Project Status

### Completed

✅ Authentication System

✅ File-Based Data Storage

✅ Role-Based Menu Routing

✅ Manager Module

✅ Administrator Module

✅ Student CRUD

✅ Student Booking System

✅ Instructor Scheduling

✅ Attendance Tracking

✅ Facilities Management

✅ Payment Persistence

✅ Issue Reporting

✅ Rating System

---

### In Progress

🔄 Documentation

🔄 UML Diagrams

🔄 Integration Testing

🔄 Console Module Refinement

---

### Upcoming

⏳ Java Swing GUI

⏳ Advanced Reporting

⏳ Testing Suite

⏳ Final Documentation

⏳ Presentation

⏳ Final Submission

---

## Development Progress

| Week | Status |
|--------|--------|
| Week 1 | ✅ Completed |
| Week 2 | ✅ Completed |
| Week 3 | ✅ In Progress |
| Week 4 | ⏳ Planned |
| Week 5 | ⏳ Planned |
| Week 6 | ⏳ Planned |

### Estimated Project Completion

```text
65% - 70% Complete
```

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

Example:

```bash
git commit -m "Implement student booking workflow"
```

---

## Next Milestones

- Attendance Enhancements
- Facilities Enhancements
- Payment Improvements
- UML Documentation
- Java Swing GUI Development
- Testing and Validation
- Final Documentation
- Presentation Preparation

---

## Contributors

SDAMS Development Team

Academic Project - Object-Oriented Programming
