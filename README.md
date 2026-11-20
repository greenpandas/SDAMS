# SDAMS - Self Defence Academy Management System

SDAMS is a Java Swing desktop application for managing a self-defence academy. It supports role-based login, file-backed business records, student and staff operations, class scheduling, attendance, payments, bookings, equipment, facility issues and ratings.

## Submission Status

The core backend, authentication and role dashboards are implemented. GUI workflows use the shared SDAMS Swing theme and text-file persistence.

| Area | Status |
|---|---|
| Authentication and role routing | Complete |
| Manager module | Complete |
| Administrator module | Complete |
| Student CRUD | Complete |
| Instructor scheduling | Complete |
| Attendance management | Complete |
| Facilities management | Complete |
| Payment management | Complete |
| Class bookings | Complete |
| Ratings | Complete |
| Java Swing GUI | Complete for submission workflows |
| Database | Not used |

## Architecture

```text
Java Swing GUI
      |
      v
Service Layer
      |
      v
FileManager
      |
      v
Text files in data/
```

SDAMS intentionally remains file-based. No database or replacement persistence framework is introduced.

## Technology

- Java
- Java Swing
- NetBeans-compatible Ant build
- `FileManager` and text-file persistence
- Git/GitHub

## Project Structure

```text
src/
  gui/             Swing forms and dashboard components
  service/         Business logic and validation
  filehandling/    FileManager text-file persistence
  model/           User, role and domain classes
data/
  users.txt        Accounts and login credentials
  students.txt     Student records
  classes.txt      Class schedules
  bookings.txt     Student bookings
  attendance.txt   Attendance records
  payments.txt     Payment records
  equipment.txt    Equipment inventory
  issues.txt       Facility issue reports
  ratings.txt      Student ratings
docs/              Audit and submission documentation
screenshots/       Presentation evidence
```

## Text-File Formats

| File | Format |
|---|---|
| `users.txt` | `ID,username,password,name,role` |
| `students.txt` | `studentID,username,password,name,Student` |
| `classes.txt` | `classID,name,date,time` |
| `bookings.txt` | `studentID,classID,status` |
| `attendance.txt` | `studentID,date,status` |
| `payments.txt` | `studentID,amount,date` |
| `equipment.txt` | `equipmentID,name` |
| `issues.txt` | `issueID,description` |
| `ratings.txt` | `studentID,instructorID,rating` |

## Demo Credentials

All passwords are `1234`.

| Role | Username |
|---|---|
| Manager | `manager` |
| Administrator | `admin1`, `admin2`, `admin3` |
| Instructor | `instructor1`, `instructor2`, `instructor3` |
| Attendance Officer | `attendance1`, `attendance2` |
| Facilities Officer | `facilities1`, `facilities2` |
| Student | `student1` through `student20` |

Demo names and records are stored in Malaysian academic context.

## Role Workflows

### Manager
- Manage administrator accounts.
- View and search staff.
- Generate revenue and staff reports.
- Review recent payments.

### Administrator
- Add, search, update and delete students.
- View staff.
- Add, search and delete payment records.
- Review live student/payment/staff KPIs.

### Instructor
- Create class schedules.
- View schedules and ratings.
- Report facility issues.

### Student
- View available classes.
- Book classes.
- View personal bookings.
- View personal payment history.
- Submit a rating from 1 to 5.

### Attendance Officer
- Record validated Present, Absent or Late attendance.
- View and filter attendance records.

### Facilities Officer
- View equipment.
- View facility issues.
- Add equipment when the corresponding GUI/service workflow is available.

## GUI

The GUI uses `UiTheme` for shared colors, typography, cards, sidebars, headers and styled tables. Dashboards provide role-specific KPIs and recent-record panels. Management forms use Swing tables and dialogs rather than introducing another presentation framework.

## Running the Application

1. Open the repository in NetBeans as an existing Java project.
2. Ensure the working directory contains `data/`.
3. Run `src/gui/LoginForm.java`, or build and run the default Ant project.
4. Sign in with a demo credential above.

Alternative from a terminal that has the JDK and Ant configured:

```bash
ant clean jar
ant run
```

When running directly with `javac`, compile under `src` and include the project working directory as the application base so relative `data/...` paths resolve correctly.

## Validation and Security

- Required text fields must be non-empty.
- Comma characters are rejected where they would corrupt text-file records.
- Student and staff IDs must exist where the workflow requires account lookup.
- Ratings accept only whole numbers from 1 to 5.
- Payment amounts must be positive numeric values.
- Payment dates must use `yyyy-MM-dd`.
- Attendance dates must use `yyyy-MM-dd`, and statuses are restricted to Present, Absent or Late.
- Duplicate usernames and IDs should be prevented during CRUD operations.

Authentication remains a demo text-file login suitable for a university desktop project. Passwords are not hashed and there is no network security layer.

## Testing Coverage

Recommended final evidence:

- Login with every role and confirm role routing.
- CRUD a temporary student and administrator record.
- Add, search and delete a payment.
- Submit an invalid and valid rating.
- Record and view attendance.
- Create a schedule and report an issue.
- Add/view equipment and view issues.
- Re-run demo data to verify exact counts.
- Run compilation and the NetBeans build before submission.

## System Limitations

- Text files provide no transactions or concurrent multi-user locking.
- Password storage is suitable for demonstration, not production deployment.
- Data integrity depends on comma-free fields and consistent record schemas.
- Local file writes depend on the application's working directory.
- There is no automated database-backed audit log.

## Future Improvements

- Add automated JUnit and UI smoke tests.
- Add CSV/PDF report export.
- Add password hashing and account-lockout controls.
- Add an audit/activity log file.
- Add transaction-safe file writes and record locking.
- Introduce optional backups and data reset tools.

## Assessment Documentation

See `docs/SDAMS_UNIVERSITY_PROJECT_UPGRADE_PLAN.md` for the P0/P1/P2 audit and `docs/SDAMS_FINAL_SUBMISSION_READINESS.md` for the final assessor report and one-week action plan.
