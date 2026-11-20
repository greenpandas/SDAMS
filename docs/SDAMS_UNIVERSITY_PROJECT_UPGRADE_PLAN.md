# SDAMS University Project Upgrade and Submission Audit

**Repository basis:** `greenpandas/SDAMS`

**Audit scope:** current Swing GUI, role routing, service classes, `FileManager`, text files, and existing documentation
**Architecture constraint:** `GUI -> Service -> FileManager -> text files`; no database migration or authentication rewrite

## Executive Summary

SDAMS already has a substantial functional foundation. The repository contains six role dashboards, role-based login routing, Swing forms, dedicated service classes, and text-file persistence. The most important submission gaps are not missing architecture; they are incomplete integration, inconsistent role-specific data, visible duplicate or legacy console workflows, and missing evidence of testing and requirements coverage.

The repository should be treated as an upgrade project for submission polish, not a rewrite. Keep `UserService.login(...)`, `MenuRouter`, and the current text-file data model stable. Prioritize role completeness and real GUI workflows over adding new technology.

## 1. Full Role Audit

### Observed role surface

| Role | Observed entry point | Implemented/current behavior | Real upgrade priority | Implementation complexity |
|---|---|---|---|---|
| Manager | `LoginForm` opens `ManagerMenuForm`; `MenuRouter` also provides a console menu | Live cards for administrator, student and revenue counts; opens `AdminManagementForm`, `StaffViewForm`, and `ReportsForm`; logout returns to login | P1 - make navigation and reports complete; add recent activity and real quick actions | Medium |
| Administrator | `LoginForm` opens `AdminMenuForm`; console route exists in `MenuRouter` | Cards for students/payments; opens student CRUD and a payment record table; no staff GUI button despite the console `manageStaff()` option | P1 - add staff view and connect the actual payment CRUD form | Medium |
| Instructor | `LoginForm` opens `InstructorMenuForm`; console route exists | Create schedule and report issue through dialogs; view schedules, ratings and issues in tables; no update/delete for schedules or issue resolution | P1 - improve validation and complete schedule/rating workflows | Medium |
| Student | `LoginForm` opens `StudentMenuForm`; console route exists | Available classes and bookings open file tables; dashboard has a bookings count and placeholder outstanding-payments value; Submit Rating has no listener | P0 - implement rating and payment actions, class booking and clear balance semantics | Medium |
| Attendance Officer | `LoginForm` routes to `AttendanceOfficerDashboard`; `AttendanceMenuForm` is also present | Record and view attendance is GUI functional; validates student IDs against `data/students.txt`, ISO dates and status; cards calculate live totals | P1 - keep one dashboard, add filtering and remove duplicate/legacy dashboard surface | Low-Medium |
| Facilities Officer | `LoginForm` opens `FacilitiesMenuForm`; console route exists | Live equipment/issue counts and file-table views; equipment and issue schemas are only two fields | P1 - add GUI add equipment/issue workflow or explicitly treat reporting as instructor-only and viewing as facilities-only | Medium |

### Role-by-role findings

#### Manager

`ManagerMenuForm` reads `data/users.txt` through `filehandling.FileManager` and counts Administrators and Students. Its revenue card calls `UiTheme.readPaymentRevenue(...)`. The "Manage Administrators" action has a real CRUD form; "View Staff" opens `StaffViewForm`; "Reports" opens `ReportsForm`. The dashboard currently has no table or recent activity and is mostly a card-and-welcome panel.

Submission requirement: complete every action, add recent administrators/payment activity, and keep report/export behavior truthful. Do not add an authorization layer that conflicts with the existing login role check.

#### Administrator

`AdminMenuForm` counts students from `data/users.txt` and payment rows from `data/payments.txt`. It opens `StudentManagementForm` and `PaymentManagementForm` only by a file-table action is currently not enough: `AdminMenuForm.btnPayments` opens `FileTableForm`, while `PaymentManagementForm` with add/search/delete exists separately and is not reached from the dashboard.

Submission requirement: wire Payments to `PaymentManagementForm`, add a Staff Information action or remove its implied console role claim, and add live refresh after CRUD.

#### Instructor

`InstructorMenuForm` uses `InstructorService.createSchedule(...)` and `InstructorService.reportIssue(...)`, which write to `data/classes.txt` and `data/issues.txt`. It validates empty fields and commas, then reports success/error in dialogs. The "View Ratings" and "View Schedule" buttons open real file tables.

Submission requirement: validate date/time/rating formats, make table values exact to the file schema, and add update/delete or clearly state create/view scope. Do not add an instructor field to `classes.txt` without a consistent file-format decision.

#### Student

`StudentMenuForm` exposes Available Classes, My Bookings and Submit Rating. The first two open `FileTableForm`; Submit Rating currently has no listener. The Outstanding Payments card is hard-coded `"--"` and says a balance field is not available. This is an honest limitation, but it is not a complete student payment workflow.

Submission requirement: either add a real payment-history view backed by `data/payments.txt` and key it to the logged-in student, or remove the Outstanding Payments claim and label the card "Payment Records". Implement rating submission against the existing `studentId,instructorId,rating` format and booking against `studentId,classId,status`.

#### Attendance Officer

`AttendanceOfficerDashboard` is the best role-specific operational GUI: it validates a student in `data/students.txt`, parses `LocalDate`, records one of Present/Absent/Late, refreshes KPI cards, and writes `data/attendance.txt`. The GUI also has a simpler `AttendanceMenuForm`, so two competing attendance dashboards exist.

Submission requirement: retain `AttendanceOfficerDashboard` and remove or clearly retire `AttendanceMenuForm`/`MenuRouter` console duplication. Add attendance-date/status filters with no schema change.

#### Facilities Officer

`FacilitiesMenuForm` calls line counts and file tables. Equipment records are `E001,Boxing Gloves`, and issue records are `I001,description`; the table buttons incorrectly assign extra "Status", "Facility", and "Description" columns where the source has only two fields. There is no Facilities GUI add form, although `FacilitiesService` has console methods for adding equipment.

Submission requirement: align exact columns and add a low-risk add-equipment dialog using `FacilitiesService`/`FileManager` if time permits. Issue status should not be invented unless the text-file format is intentionally extended.

## 2. Real-World Upgrade Priorities

Priority and complexity:

- **P0:** critical to submission/demo correctness.
- **P1:** high value and visible in the final demo.
- **P2:** useful but secondary.
- **Complexity L:** low; M:** medium; H:** high.

| Upgrade | Grounded need | User value | Priority | Complexity |
|---|---|---|---|---|
| Wire student rating submission to `ratings.txt` | Submit Rating button has no listener; service has console `submitRating()` only | Students can complete a supported workflow | P0 | M |
| Route Administrator Payments to `PaymentManagementForm` | Dashboard opens generic `FileTableForm`, while a complete payment form exists elsewhere | Demonstrates real add/search/delete integration | P0 | L |
| Remove/fix misleading facility columns | Equipment and issue files have two fields; GUI passes four/three column names | Prevents false or blank UI claims | P0 | L |
| Define student Outstanding Payments behavior | Dashboard shows `"--"` because no balance field exists | Removes misleading KPI | P0 | L |
| Make all dashboard KPIs live and role-specific | Some cards read files; others count generic lines | Reliable demo evidence | P1 | L-M |
| Add a shared recent activity panel | There is no meaningful activity list or most-recent-record view | Makes dashboards operational | P1 | M |
| Complete role-specific booking workflow | `StudentService.bookClass()` exists only in console mode | Enables GUI booking and status viewing | P1 | M |
| Add update/delete/cancel only where schema supports it | Existing `PaymentService.deletePayment` and student/admin CRUD support removal | Improves real operations | P1 | M |
| Add sort/search/result count to `FileTableForm` | Generic table is plain and has only line loading/empty catch | Faster inspection and professional behavior | P1 | M |
| Centralize exception and empty-state handling | Several catches ignore or print exceptions; empty states are inconsistent | Prevents misleading success and silent failure | P1 | M |
| Add text-file backup/restore for demo | Persistence has no backup workflow | Prevents demo data loss | P2 | L |
| Add CSV export to Reports | Export button only shows an "can be added" message | Useful but not required by architecture | P2 | M |
| Add issue status or equipment condition | No matching third field exists | Avoids schema invention | P2 | H |
| Add an audit/activity log | No existing log file or service | Traceability, but outside core requirements | P2 | M |

## 3. Exact Button and Form Audit

### LoginForm

- **Login:** authenticates through `UserService.login(...)`, then routes to six role dashboards.
- **Exit:** calls `System.exit(0)`.
- **Username Enter:** moves focus to password.
- **Password Enter:** invokes login.
- **Finding:** blank and invalid credential dialogs are present. There is no role/authorization rewrite; the exact role strings are Manager, Administrator, Instructor, Student, AttendanceOfficer and FacilitiesOfficer.
- **Upgrade:** add login-card sizing and consistent error dialogs; ensure Enter-key behavior remains; do not change credential storage or authentication.

### Manager

| Control | Observed result | Finding/action |
|---|---|---|
| Overview | No action listener | Add overview refresh or remove |
| Manage Administrators | Opens real `AdminManagementForm` | Keep; improve duplicate-ID/username validation |
| View Staff | Opens real `StaffViewForm` | Keep; add refresh/search/Back |
| Reports | Opens real `ReportsForm` | Keep; implement export or change placeholder button label |
| Logout | Opens login and disposes dashboard | Add confirmation consistent with attendance logout |

### Administrator

| Control | Observed result | Finding/action |
|---|---|---|
| Overview | No action listener | Make it refresh dashboard or remove |
| Manage Students | Opens real CRUD form | Keep; prevent duplicate users and password exposure |
| Payments | Opens generic `FileTableForm` | Replace with `PaymentManagementForm` |
| Logout | Opens login and disposes | Add confirmation |

### Student

| Control | Observed result | Finding/action |
|---|---|---|
| Overview | No action listener | Refresh role dashboard or remove |
| Available Classes | Shows `data/classes.txt` with four supplied columns | Keep; filter/cancel not present |
| My Bookings | Shows `data/bookings.txt` with three supplied columns | Keep; add cancel only with existing status format |
| Submit Rating | No listener | P0: implement the existing `studentId,instructorId,rating` write |
| Logout | Opens login and disposes | Add confirmation |

### Instructor

| Control | Observed result | Finding/action |
|---|---|---|
| Create Schedule | Opens 4-field dialog and calls `InstructorService.createSchedule(...)` | Validate date/time and duplicate class ID |
| View Schedule | Opens exact 4-column file table | Keep |
| Report Issue | Opens 2-field dialog and calls `InstructorService.reportIssue(...)` | Validate ID/description; no status field exists |
| View Ratings | Opens 3-column file table | Keep and add rating 1-5 validation |
| Logout | Opens login and disposes | Add confirmation |

### Attendance Officer

| Control | Observed result | Finding/action |
|---|---|---|
| Record Attendance | Validated 3-field dialog | Keep; date/status/student checks are good |
| View Attendance | Opens exact 3-column file table | Add status/date filters |
| Logout | Confirmation, login, dispose | Good pattern to reuse elsewhere |

### Facilities Officer

| Control | Observed result | Finding/action |
|---|---|---|
| Equipment | Supplies 3 columns for a 2-field file | P0: change to `Equipment ID, Name` |
| Facility Issues | Supplies 4 columns for a 2-field file | P0: change to `Issue ID, Description` |
| Logout | Opens login and disposes | Add confirmation |

### Management forms

`AdminManagementForm` and `StudentManagementForm` use a 590px table plus form, 2-column action grids, row selection, and file-backed CRUD. Both load password values into `JPasswordField`; this is inappropriate for a professional account-editing view and can expose credentials through local form state. Update operations should require or explicitly preserve a password, not silently display one. Student add/update/delete can reject or persist correctly; Administrator GUI methods do not currently return found/result states.

`PaymentManagementForm` is stronger: it validates required fields, numeric amount, positive amount and `yyyy-MM-dd`, supports search, delete and row selection, and displays a no-records state. It uses `Double` for parsing; for an academic submission this is acceptable if the stored and displayed amount is two-decimal text, but the production upgrade should use decimal-safe formatting consistently.

`ReportsForm` is functional for total revenue, average payment, payment count and staff counts. "Export" is currently not implemented. `StaffViewForm` loads all non-student accounts, but has no Back, Refresh or search control.

## 4. Professional Dashboard Overhaul

### Shared wireframe

```text
+----------------------+------------------------------------------------+
| SDAMS                | Page title + subtitle                          |
| ROLE                 |                                                |
|                      | [KPI 1] [KPI 2] [KPI 3]                       |
| Overview             |                                                |
| Role action A        | [Recent activity table]  [Quick actions]      |
| Role action B        |                                                |
| Role action C        |                                                |
|                      |                                                |
| Logout               |                                                |
+----------------------+------------------------------------------------+
```

Use the existing 230px sidebar and 1180x720 Swing base. Preserve `UiTheme.createSidebar`, `createHeader`, `createStatCard`, and `createTableScroll` as shared helpers. Remove hard-coded/placeholder content only after the backing file or service exists.

### Manager dashboard

- **KPI cards:** Total Administrators (`users.txt` role count), Total Students (`users.txt` role count), Total Revenue (`payments.txt` sum).
- **Recent table:** Administrator ID, name, username; last payment ID, student ID, amount, date.
- **Quick actions:** Manage Administrators, View Staff, Open Reports.
- **Recent activity:** "Latest payment added", "Latest administrator account", "Most recent issue" is not a manager core metric until an activity log exists.

### Administrator dashboard

- **KPI cards:** Total Students, Payment Records, Total Staff.
- **Table:** Recent payments: Student ID, Amount, Date.
- **Quick actions:** Manage Students, Manage Payments, View Staff.
- **Recent activity:** Latest student account and latest payment.

### Instructor dashboard

- **KPI cards:** Total Classes, Total Ratings, Reported Issues.
- **Table:** Next/later classes: Class ID, Name, Date, Time.
- **Quick actions:** Create Schedule, View Schedule, Report Issue.
- **Recent activity:** Latest schedule and latest issue record.

### Student dashboard

- **KPI cards:** My Bookings, Available Classes, Payment Records.
- **Table:** My bookings: Student ID, Class ID, Status.
- **Quick actions:** View Classes, My Bookings, Submit Rating, View Payments.
- **Recent activity:** Latest rating and latest relevant class.

Do not show Outstanding Payments until a balance or due-payment column exists in a current text file.

### Attendance Officer dashboard

- **KPI cards:** Total Records, Present, Absent/Late.
- **Table:** Recent attendance: Student ID, Date, Status.
- **Quick actions:** Record Attendance, View Records, Filter by date/status.
- **Recent activity:** Latest date/status entries.

The existing `AttendanceOfficerDashboard` already implements the Present/Absent/Late card structure.

### Facilities Officer dashboard

- **KPI cards:** Total Equipment, Open/Reported Issues.
- **Table:** Equipment ID/Name and Issue ID/Description.
- **Quick actions:** View Equipment, View Issues, Report Equipment if a GUI is added.
- **Recent activity:** Latest issue and latest equipment record.

Do not add status until issue rows are extended from `issueId,description` to an agreed schema.

## 5. High-Impact One-Week Features

Ranked by submission impact:

| Rank | Feature | Why it matters | Existing repository path | Effort |
|---|---|---:|---|---|
| 1 | Fix all visible button/schema defects | Removes the clearest evidence of incomplete work | `AdminMenuForm`, `FacilitiesMenuForm`, `StudentMenuForm` | 0.5 day |
| 2 | Wire Student Rating and My Payments views | Completes role-specific workflows | `StudentMenuForm`, `StudentService`, text files | 0.5-1 day |
| 3 | Standardize `PaymentManagementForm` navigation | Uses existing functional service code | `AdminMenuForm`, `PaymentManagementForm`, `PaymentService` | 0.5 day |
| 4 | Improve validation and duplicate-ID handling | Prevents corrupt text-file records | CRUD services and GUI forms | 1 day |
| 5 | Unify dashboards and remove duplicate attendance/console drift | Makes role navigation defensible | `AttendanceOfficerDashboard`, `AttendanceMenuForm`, `LoginForm` | 0.5 day |
| 6 | Add live recent-record panels and result counts | Adds operational feel without architecture changes | `UiTheme`, `FileTableForm`, role dashboards | 1 day |
| 7 | Produce screenshots/test evidence/documentation | Converts implementation into assessed evidence | `docs/`, `README.md` | 1-1.5 days |
| 8 | Instructor/Facilities create forms and exact table schemas | Completes more role surfaces | `InstructorMenuForm`, `FacilitiesMenuForm`, services | 1 day |
| 9 | CSV report export | High presentation value but secondary to functional correctness | `ReportsForm` | 0.5 day |
| 10 | Demo data reset/backup script | Safe repeatable demonstration | `data/` | 0.5 day |

## 6. MUST / SHOULD / OPTIONAL Submission Roadmap

### MUST

1. Fix Facilities equipment and issue column mismatches.
2. Replace Administrator Payments generic table with `PaymentManagementForm`.
3. Implement or remove Student Submit Rating; no visible dead button.
4. Remove Outstanding Payments `"--"` or clearly reframe it as Payment History.
5. Remove/no-action Overview buttons or add refresh behavior.
6. Confirm all six role logins route to the intended dashboard.
7. Ensure Student/Admin/Manager CRUD rewrites `data/users.txt` correctly and does not duplicate or lose users.
8. Prevent comma-containing fields and invalid class, issue, rating and attendance values.
9. Reconcile all role dashboards against their actual text-file schemas.
10. Run role smoke tests and document pass/fail evidence.
11. Update README and add screenshots, run instructions and text-file schemas.
12. Produce use-case, architecture and current class diagrams.

### SHOULD

1. Add search/filter/result count to file tables.
2. Add recent-record panels to role dashboards.
3. Add confirmation dialogs to destructive actions and logout consistently.
4. Add success/error states that preserve the affected ID or record.
5. Add Student payment history keyed to the student ID.
6. Add Instructor scheduling duplicate-ID and date/time validation.
7. Add a real Staff search/refresh/Back surface.
8. Implement report export or remove the Export control.
9. Add clear empty states instead of an artificial table row.
10. Add automated or repeatable smoke-test documentation.

### OPTIONAL

1. CSV export.
2. Theme toggle.
3. Booking cancellation.
4. Issue status or equipment condition.
5. Audit/activity log.
6. Charts and trend reports.
7. Demo reset utility.

Do not add a database, cloud service, QR attendance, biometric login or mobile application. They would undermine the required text-file architecture and require scope beyond the one-week upgrade.

## 7. 100-Point Final Project Assessment

| Category | Weight | Awarded | Rationale |
|---|---:|---:|---|
| Requirements and role coverage | 15 | 12 | Six roles and core modules exist, but not every role has complete GUI behavior |
| Architecture and separation | 15 | 13 | GUI, service, `FileManager`, and text files are visibly separated |
| Authentication and role routing | 10 | 8 | Login and role routing work, but password/plain text storage remains the existing academic limitation |
| CRUD and persistence | 15 | 11 | Student/admin CRUD and payment CRUD exist; validation and duplicate protection are incomplete |
| GUI consistency and usability | 20 | 12 | Shared theme exists, but dead buttons and schema mismatches remain |
| Data/report accuracy | 10 | 7 | Revenue/count reports are real; student balances and facilities status are misleading or absent |
| Testing evidence | 10 | 4 | Code has partial validation, but no visible test suite or test evidence |
| Documentation and submission evidence | 5 | 3 | README and existing docs exist, but diagrams/screenshots/run/test evidence need synchronization |
| **Total** | **100** | **70** | |

### Exact submission-blocking weaknesses

1. **Student Submit Rating is visibly nonfunctional.** It is a sidebar button but has no listener.
2. **Facilities table schemas are wrong.** Equipment and issues have two fields; GUI forms show extra columns.
3. **Administrator Payments does not open the available CRUD form.** It opens a generic file table instead.
4. **Student Outstanding Payments is not backed by data.** It shows `"--"` and explicitly states the balance field is unavailable.
5. **Role navigation has no dead-button rule.** Overview and other controls have no action; the demo will expose them.
6. **Demo evidence is incomplete.** No visible screenshot set, test report or diagram set is present in the repository audit.
7. **Text-file edge cases are not protected.** Many services use comma splitting and do not guard malformed lines or duplicate IDs/usernames.
8. **Two attendance dashboards remain.** `AttendanceOfficerDashboard` and `AttendanceMenuForm` are overlapping implementations.
9. **Password handling exposes existing plain-text credentials in edit fields.** This is a product and security weakness, even within the academic text-file constraint.
10. **Documentation and README claims do not fully match the GUI.** The README can imply in-progress migration and is not enough as a tested submission package.

### Distinction-level targets

An 82-88 range is realistic after removing submission blockers and completing:

- all role action wiring;
- exact table schemas;
- payment/student/instructor operational flows;
- validation and duplicate checks;
- screenshots;
- test evidence;
- requirements traceability;
- UML/use-case/architecture diagrams;
- a clear, repeatable demo script.

No new database or authentication rewrite is needed to reach this range.

## 8. Final Implementation and Assessment Checklist

### Repository and architecture

- [ ] All application changes preserve GUI -> Service -> `FileManager` -> text files.
- [ ] No database or external API is introduced.
- [ ] Authentication remains based on `data/users.txt` and existing role names.
- [ ] A backup of `data/` exists before demo preparation.
- [ ] Malformed or headerless data are reviewed before use.

### Role smoke test

- [ ] Manager login opens `ManagerMenuForm`; Administrator/Staff/Reports buttons open real forms.
- [ ] Administrator login opens `AdminMenuForm`; Students and Payments perform real workflows.
- [ ] Instructor login opens `InstructorMenuForm`; schedule, issue and rating table workflows work.
- [ ] Student login opens `StudentMenuForm`; Available Classes, My Bookings and Rating have real behavior.
- [ ] Attendance login opens one selected attendance dashboard; record and view operations persist.
- [ ] Facilities login opens exact two-column Equipment and Issue tables.
- [ ] Every Logout returns to login and no stale dashboard remains open.

### Text-file regression

- [ ] `users.txt`: 5 fields, `ID,username,password,name,role`.
- [ ] `students.txt`: 5 fields, no duplicate role records lost.
- [ ] `staff.txt`: no GUI overwrite from `users.txt` unexpectedly.
- [ ] `classes.txt`: `classId,name,date,time`.
- [ ] `bookings.txt`: `studentId,classId,status`.
- [ ] `attendance.txt`: `studentId,date,status`.
- [ ] `payments.txt`: `studentId,amount,date`.
- [ ] `equipment.txt`: `equipmentId,name`.
- [ ] `issues.txt`: `issueId,description`.
- [ ] `ratings.txt`: `studentId,instructorId,rating`.

### Submission evidence

- [ ] Login and all role dashboards captured.
- [ ] CRUD add/search/update/delete captured.
- [ ] Payment, attendance, scheduling, booking, issue and rating workflows captured.
- [ ] Success, warning and confirmation dialogs captured.
- [ ] Empty/no-match states captured.
- [ ] Test checklist includes expected result and pass/fail evidence.
- [ ] Use-case, class, architecture and key sequence diagrams are included.
- [ ] README contains JDK, NetBeans, build, run and demo steps.
- [ ] Final report/priorities are linked in `README.md`.
