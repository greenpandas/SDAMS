# SDAMS Final Submission Readiness Report

**Prepared:** 20 November 2026  
**Repository:** greenpandas/SDAMS  
**Target:** University final submission and presentation

## Readiness Score

| Category | Score | Maximum |
|---|---:|---:|
| Architecture and role separation | 18 | 20 |
| Feature completeness | 17 | 20 |
| Data quality and consistency | 15 | 15 |
| GUI usability and navigation | 13 | 15 |
| Validation and duplicate protection | 9 | 10 |
| Documentation and README | 8 | 10 |
| Build and test evidence | 4 | 10 |
| **Total** | **84** | **100** |

**Overall status: Demonstration-ready, but the final build could not be executed because Java and Ant are not available on the current PATH.**

## Final Implementation Reconciliation

- Final demo data verified: 31 users containing 1 Manager, 3 Administrators,
  3 Instructors, 2 Attendance Officers, 2 Facilities Officers and 20 Students.
- `students.txt` and `staff.txt` are synchronized to the 20-student and 11 non-student
  account datasets.
- Final relation files contain 20 class bookings, 20 attendance records, 20 payments,
  15 equipment records, 10 facility issues and 20 ratings.
- P0/P1 implementation includes student booking/rating/payment views, administrator
  staff/payment integration, facilities equipment entry, logout confirmations, and
  duplicate/date/amount/schedule/attendance validation.
- No JDK or Ant executable was discoverable via `PATH` or the common Windows install
  locations, so source compilation and Ant build execution remain environment-limited.

## Strengths

- Preserves the required Swing -> Service -> FileManager -> text-file architecture.
- Covers all six role areas with role-specific dashboards and navigation.
- Uses consistent Malaysian demo names and a realistic academy dataset.
- Contains student, staff, payment, attendance, booking, equipment, issue and rating records.
- Includes authentication, CRUD workflows, reports and direct data visibility.
- Keeps no database or external persistence dependency.
- Includes facilities add-equipment, student class-booking/rating/payment, and role logout confirmation flows.
- Adds duplicate checks for users, students, administrators, ratings, schedules, issues, equipment and attendance.
- Adds date and time validation for schedules, date validation for payments, and positive payment amount validation.

## Remaining Weaknesses

### Critical
 
- Build/test execution is the remaining release blocker on this machine:
  `java` and `ant` are not currently available on `PATH`; no JDK/Ant binary could be invoked.
- `students.txt` can drift from `users.txt` because CRUD GUI operations update `users.txt`
  while `students.txt` is a demo/reference copy.
- Older console-style service methods retain broader compatibility behavior and should be
  retired or routed through the same service validation if a follow-up hardening pass is done.

### Important

- Authentication passwords are stored as plain text and are not designed for production use.
- Text-file persistence has no locking, transaction or rollback semantics.
- Attendance duplicate prevention and ratings duplicate-per-student/instructor enforcement
  should be verified across GUI and service entry points.
- Several generic forms may need final runtime clipping and component-resizing checks.
- Automated test coverage is still limited.

### Optional

- Replace in-place text records with a database after the university release.
- Add password hashing and audit history.
- Add PDF/CSV report exports and richer chart widgets.
- Expand JUnit and GUI test automation.

## Critical Action Plan

1. Build with Ant/JDK and run the login and role-routing demonstration.
2. Use the demo credentials exactly as documented.
3. Verify every role can log out, return to login and move Back where appropriate.
4. Confirm `users.txt`, `students.txt`, `payments.txt`, `attendance.txt`, `bookings.txt`,
   `classes.txt`, `equipment.txt`, `issues.txt` and `ratings.txt` remain internally consistent.

## Important Action Plan

1. Validate duplicate student/staff IDs and usernames before save/update.
2. Validate attendance one record per student/date and rating one record per student/instructor.
3. Validate dates as real `YYYY-MM-DD` values and class times as `HH:mm`.
4. Reject zero, negative, malformed or excessive payment amounts.
5. Verify add-equipment, payment, booking and rating dialogs refresh live dashboard values.

## Optional Action Plan

- Prepare screenshots for the login, each role dashboard and one CRUD/report workflow.
- Record a 5-10 minute presentation script.
- Remove obsolete console-only code only after a complete build and release test.
- Add a small JUnit suite for validation and file operations.
