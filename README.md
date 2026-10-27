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
- Java Swing (Upcoming)
- Git & GitHub
- Text File Storage

---

## Current Project Structure

```text
SDAMS
│
├── data
├── diagrams
├── docs
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

### Polymorphism

Planned during module implementation.

### Abstraction

Planned during service implementation.

---

## Completed Features

### Week 1

✅ Project Setup

✅ GitHub Repository Configuration

✅ Package Structure

✅ Model Classes

✅ Service Layer Classes

✅ File Handling Layer

✅ Documentation Structure

✅ UML Planning

---

### Week 2 Progress

✅ User Model Completed

✅ User Authentication

✅ Login Validation

✅ FileManager User Reading

✅ FileManager User Saving

✅ Console-Based Login System

✅ Role-Based Menu Routing

✅ Interactive Manager Menu

✅ Manager Service Layer

✅ Administrator Management Menu

✅ Add Administrator

✅ Save Administrator To users.txt

✅ View Administrators

✅ Search Administrator

✅ Update Administrator Skeleton

✅ Delete Administrator Skeleton

---

## Login Flow

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
Role-Based Menu
```

---

## Current Status

### Completed

- User Authentication
- Role Routing
- Manager Menu System
- Administrator CRUD Foundation
- File Handling
- Data Storage Design

### In Progress

- Administrator CRUD Completion
- Staff Management
- Reports

### Upcoming

- Java Swing GUI
- Attendance Module
- Booking Module
- Payment Module
- Reporting System

---

## Team Workflow

Before coding:

```bash
git pull
```

After completing a feature:

```bash
git add .
git commit -m "Feature description"
git push
```

---

## Development Roadmap

### Week 2
- Login System
- User Management
- Manager Module

### Week 3
- Administrator Module
- Student Management

### Week 4
- Booking
- Attendance
- Scheduling

### Week 5
- Facilities
- Payments
- Reports

### Week 6
- Documentation
- Testing
- Presentation
- Final Bug Fixes
