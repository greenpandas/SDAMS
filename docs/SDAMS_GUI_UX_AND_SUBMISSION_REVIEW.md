# SDAMS: GUI/UX and Final Submission Review

## 1. Full GUI Review

### Confirmed from source

| Form | Current finding | Exact fix |
|---|---|---|
| `LoginForm` | 920x620 root and 440x520 card; 72px logo plus stacked labels/fields/buttons may clip at 100% DPI or Windows scaling | Use 960x680 minimum; card 460x560; 32px outer padding; add vertical gap control; reduce logo to 64px or increase card height to 580px; make window resizable with a 860x620 minimum |
| `LoginForm` | Card uses 76px horizontal padding despite 350px fields (438px content); 440px card is tight | Set card width 460px and fields/buttons 368px; add 12px spacing between field label and input |
| `LoginForm` | Seven font sizes and text appears vertically dense | Use only title 28px, section 18px, label 13px bold, input 14px, and button 14px bold |
| `LoginForm` / all dashboards | Non-resizable or fixed sizing | Use `setMinimumSize(960,640)`; set `1180x720` preferred; allow resizing and let Swing layouts reflow |
| All dashboards | Fixed 230px sidebar and 1180x720 window | Retain 230px desktop sidebar; collapse labels into icons only below 1000px if responsive behavior is added |
| All dashboards | 3 cards use `GridLayout(1,3)` with no wrap | Use `GridLayout(1,3)` for >=1050px and `GridLayout(2,2)` below 1050px; apply 24px side margins and 16px gaps |
| `ManagerMenuForm` | Staff and Reports buttons show placeholder dialogs | Wire to real `StaffViewForm` and `ReportsForm`, or remove until implemented |
| `StudentMenuForm` | Submit Rating and Overview actions are absent or incomplete | Add a functional rating dialog/table; attach Overview to the dashboard |
| `ReportsForm` / `StaffViewForm` | Placeholder label windows and `pack()` sizing | Replace with real header, KPI cards, table, refresh/search/back controls, and 900x560 preferred size |
| Management forms | 590px table plus 4-field form on 1180px window | Preserve 590px table; set 480px form; at widths below 1050px stack form below table |
| Management forms | 2-column action grid produces no visual primary hierarchy | Primary Add/Update, Secondary Search/View All/Clear, Danger Delete right-aligned; button width 120-140px |
| Management forms | Table selection does not populate every field consistently | On row selection, populate ID/name/username/password only if intended; disable password display or clear it and require re-entry for updates |
| Generic `FileTableForm` | Split-by-comma only; no typed columns, sorting, search or empty state | Add typed `Object` columns, `JTable.AUTO_RESIZE_*`, sorter, row count and “No records found” panel |
| Facilities dashboard | Equipment and issue tables are misaligned to actual 2-field records | Set Equipment columns `Equipment ID, Equipment Name`; set Issues `Issue ID, Description` |
| Stale `.form` files | NetBeans `.form` layouts contain old areas/text areas while runtime Java builds different layouts | Remove unused `.form` files or regenerate them to match runtime code; delete stale `txtAdminList` declarations |
| Navigation | Windows dispose/open instead of maintaining a parent-child relationship | Keep one dashboard frame and replace the centre panel, or track owner and use `dispose` consistently |

### Global exact layout rules

- Window minimum: `960x640`; dashboard preferred: `1180x720`.
- Sidebar: `230px`, header `72px`, 24px content margins, 16-18px card gaps.
- CRUD body: 55% table / 45% form with 18px gap.
- Dialog minimum: `420x260`, destructive actions require confirmation.
- Preferred scroll panes must be set on every table and text region.
- `JFrame.setResizable(true)` where content is operational; login can remain 960x680 but should also allow resize.

## 2. Professional SDAMS Design System

### Palette

| Role | Hex | Suggested Swing color |
|---|---|---|
| Sidebar | `#1E293B` | `UiTheme.SIDEBAR` |
| Window background | `#0F172A` | `UiTheme.WINDOW` |
| Card background | `#1E293B` | `UiTheme.CARD` |
| Input background | `#334155` | `UiTheme.INPUT` |
| Primary | `#2563EB` | `UiTheme.PRIMARY` |
| Success | `#22C55E` | `UiTheme.SUCCESS` |
| Warning | `#F59E0B` | `UiTheme.WARNING` |
| Danger | `#EF4444` | `UiTheme.DANGER` |
| Text | `#F8FAFC` on dark surfaces; `#0F172A` on light modal surfaces |
| Muted text | `#94A3B8` |
| Border | `#475569` |

### Typography

Use Segoe UI throughout:

- H1: Bold 26px
- H2: Bold 20px
- H3: Bold 16px
- Body: Plain 14px
- Label: Bold 13px
- Input: Plain 14px
- Table header: Bold 13px
- Table body: Plain 13px
- Caption: Plain 12px

Avoid more than three font sizes on one screen. Add helper text in caption style.

### Component standards

- Inputs: 44px height, 8px radius, `#334155`, `#475569` border, 12px horizontal padding.
- Primary button: 44px height, 12-18px horizontal padding, primary fill.
- Success: Add/Create actions.
- Warning: Review/Reset/Status actions.
- Danger: Delete/Logout/Cancel, with confirmation.
- Neutral: Back/Clear, `#475569`.
- Table: 38px rows, 42px header, no vertical grid, 1px horizontal grid `#334155`, alternating rows optional.
- Card: 16px border radius, 18px padding, 4px left accent or 1px border, no excessive shadow.
- Dialog: 24px padding, title separated from body, footer right-aligned.

Update `UiTheme.java` constants to the requested palette; remove `CONTENT = 248,250,252` and white card constants from dashboard implementation. `CARD`, `WINDOW`, `INPUT`, `BORDER`, `PRIMARY`, `SUCCESS`, `WARNING`, `DANGER` should be central constants.

## 3. Dashboard Redesign

### Common dashboard structure

```text
+------------------+-----------------------------------------------+
| SDAMS            | [Header / breadcrumb]                         |
| Role             |                                               |
|                  | [3 statistic cards]                           |
| Overview         |                                               |
| Module A         | [Recent records / quick actions]              |
| Module B         |                                               |
|                  |                                               |
| [Logout]         |                                               |
+------------------+-----------------------------------------------+
```

### Manager

Cards: **Total Administrators**, **Total Students**, **Total Revenue**. Buttons: **Manage Administrators**, **View Staff**, **Reports**, **Logout**.

Bottom panel:
- Recent administrator additions
- Recent revenue
- Quick action row with “Add Administrator” and “Export Revenue Report”

### Administrator

Cards: **Total Students**, **Total Payments**. Buttons: **Manage Students**, **Manage Staff**, **Manage Payments**, **Logout**.
Bottom panel:
- Recent payments table
- Student enrolment summary

### Instructor

Cards: **Total Classes**, **Total Ratings**.
Sidebar: Overview, Scheduling, Ratings, Report Issue, Logout.

### Student

Cards: **Total Bookings**, **Recent Classes**.
Sidebar: Overview, View Classes, My Bookings, Payments, Ratings, Logout.

### Facilities

Cards: **Total Equipment**, **Open Issues**.
Sidebar: Overview, Equipment, Issues, Logout.

### Attendance

Cards: **Attendance Records** plus optional Present/Late/Absent KPIs.
Sidebar: Overview, Record Attendance, View Records, Logout.

Stat card implementation: title 13px muted, value 28px bold, accent 12px muted. Every card must show live service/file data, not hard-coded values.

## 4. JTextArea-to-JTable Conversion

### Administrators

Columns: `Administrator ID`, `Name`, `Username`, `Role`, `Status`.
Source: `users.txt`/administrator service. Password must not be displayed.

### Students

Columns: `Student ID`, `Name`, `Username`, `Role`, `Status`.
Recommended add a filter for name/ID and row selection to populate the edit form.

### Payments

Columns: `Student ID`, `Student Name`, `Amount`, `Date`.
Use `BigDecimal` for display/business values, not floating point. Student Name requires join from `users.txt`/`students.txt`; keep raw file format unchanged.

### Attendance

Columns: `Student ID`, `Student Name`, `Date`, `Status`.
Date/Status typed columns, sortable date, optional Present/Late/Absent filter.

### Equipment

Columns: `Equipment ID`, `Equipment Name`.
Do not request nonexistent status/condition fields unless a matching third text-file field is added.

### Ratings

Columns: `Student ID`, `Student Name`, `Instructor ID`, `Instructor Name`, `Rating`.
Rating renderer: 1-5 text or filled stars; keep underlying integer.

### Bookings

Columns: `Student ID`, `Student Name`, `Class ID`, `Class Name`, `Status`.
Status colors: Booked blue/green, Cancelled red/muted.

Common `JTable` settings:

- `setFillsViewportHeight(true)`
- `setRowHeight(38)`
- `setAutoCreateRowSorter(true)`
- set preferred widths and disable unwanted resizing
- custom DefaultTableModel with `isCellEditable=false`
- render ID/text left and amount/right
- add “Clear search”, “Refresh”, “Record count”, and empty-state text

## 5. Missing Features Audit

### Quick Win

1. Functional View Staff table.
2. Functional Reports with total payments/revenue and simple role counts.
3. Logout confirmation and return-to-login confirmation.
4. Search/clear feedback and result count.
5. Required-field markers and inline validation.
6. Empty-state messages for all tables.
7. Recent records panel on dashboards.
8. Fix equipment/issues column counts and ratings newline/csv corruption.
9. Add password visibility toggle and login Enter-key support.
10. Page titles/breadcrumbs and consistent Back buttons.

### Medium Effort

1. Payment creation workflow for Administrators.
2. Instructor scheduling CRUD UI.
3. Student payment history and booking cancellation.
4. Rating submission dialog with selected instructor/class.
5. Attendance summary and filtering by date/status.
6. Equipment issue status (Open, In Progress, Closed) requires schema decision and text-file format extension.
7. Revenue report and CSV export; CSV export is useful but text-file persistence remains unchanged.
8. Role-aware navigation shell shared by dashboards.
9. Dashboard live values instead of placeholder/hardcoded figures.
10. Basic activity log text file for demo traceability.

### Not Worth Doing Before Submission

- Database migration
- Internet/cloud login
- Mobile application
- Complex report designer
- Real-time notifications
- Complex role permission editor
- Web API or microservices
- Advanced analytics, ML or QR attendance
- Biometric attendance
- Public deployment

## 6. Usability Improvements

- **Confirmation:** Delete Administrator/Student, logout, booking cancellation, payment reset, and “Clear form”.
- **Errors:** `JOptionPane.showMessageDialog(..., JOptionPane.ERROR_MESSAGE)` for missing fields, duplicate IDs, malformed text files and invalid numeric values.
- **Success:** `SUCCESS_MESSAGE` for add/update/delete/payment/booking/rating operations; include the affected ID.
- **Search feedback:** “X records found” or “No match for ‘...’”; keep table and search field visible.
- **Empty states:** “No students registered”, “No payments found”, “No attendance records”, “No open issues”.
- **Validation:** Trim text; reject blank fields; ID pattern; username uniqueness; password minimum 8 characters; amount >0; dates valid `yyyy-MM-dd`; rating 1-5.
- **Required fields:** Red asterisk or “Required” caption; validate on submit, not after form submission.
- **Focus:** Select username on login; use Enter to submit; move focus to first invalid field.
- **Destructive action:** Confirm with ID/name and do not allow accidental Enter dismissal.

## 7. Demo Data

Complete CSV datasets are generated in `SDAMS-demo-data/` beside this review:

| File | Records |
|---|---:|
| `users.txt` | 25 |
| `students.txt` | 35 |
| `staff.txt` | 15 |
| `attendance.txt` | 30 |
| `payments.txt` | 25 |
| `classes.txt` | 20 |
| `bookings.txt` | 20 |
| `equipment.txt` | 15 |
| `issues.txt` | 10 |
| `ratings.txt` | 20 |

Before copying these files into `data/`:

- Back up existing data.
- Remove any existing header line only if the service reader parses headerless records.
- Use the exact 5-field user format `ID,USERNAME,PASSWORD,NAME,ROLE`.
- Use `STUDENT_ID,DATE,STATUS` for attendance.
- Use `STUDENT_ID,AMOUNT,DATE` for payments.
- Use `CLASS_ID,NAME,DATE,TIME` for schedules.
- Use `STUDENT_ID,CLASS_ID,STATUS` for bookings.
- Use `EQUIPMENT_ID,NAME` for equipment.
- Use `ISSUE_ID,DESCRIPTION` for issues.
- Use `STUDENT_ID,INSTRUCTOR_ID,RATING` for ratings.

Do not preserve the existing malformed ratings line `S001,I001,5S001,I001,2`; replace it with separate newline records as generated.

## 8. Final Testing Checklist

### Authentication

- [ ] Valid Manager, Administrator, Instructor, Student, Attendance Officer and Facilities Officer login
- [ ] Invalid username; invalid password; blank username; blank password
- [ ] Correct dashboard by role
- [ ] Enter key submits login
- [ ] Logout returns to login
- [ ] Exit closes application

### Manager

- [ ] Add valid Administrator
- [ ] Reject duplicate Administrator ID/username
- [ ] Reject blank required fields
- [ ] Search by ID, name and username
- [ ] Search with no match shows feedback
- [ ] Update Administrator and persist to `users.txt`
- [ ] Delete with confirmation and persisted removal
- [ ] Cancel delete leaves record unchanged
- [ ] View Staff loads every non-student role
- [ ] Reports show correct counts/revenue

### Administrator

- [ ] Add, search, update and delete Student
- [ ] Student table reload after CRUD
- [ ] View Staff by role
- [ ] Add valid payment
- [ ] Reject zero/negative/non-numeric payment
- [ ] Payment table shows 3 fields correctly
- [ ] Student and payment IDs remain linked

### Instructor

- [ ] Add a schedule/class
- [ ] Reject duplicate class ID/invalid date/time
- [ ] View schedule
- [ ] Update/delete schedule if supported
- [ ] Report facility issue
- [ ] Issue persists to `issues.txt`
- [ ] Ratings table loads and aggregates 1-5 values

### Student

- [ ] View available classes
- [ ] Book class once
- [ ] Duplicate/cancelled booking behavior is correct
- [ ] Cancel a booking if implemented
- [ ] View payment history
- [ ] Submit 1-5 rating
- [ ] Rating persists to `ratings.txt`

### Facilities

- [ ] Add equipment
- [ ] Reject duplicate equipment ID
- [ ] View 15 equipment records
- [ ] View issue description
- [ ] Issue table is correctly two columns
- [ ] Equipment/issues survive application restart

### Attendance

- [ ] Record Present, Absent and Late
- [ ] Invalid student ID is rejected
- [ ] Invalid date is rejected
- [ ] View all attendance
- [ ] Filter by student/date/status
- [ ] Attendance persists and reloads

### Cross-cutting

- [ ] No clipped or overlapping controls at 1180x720 and 960x640
- [ ] Tables have scroll bars
- [ ] Back/Logout navigation works
- [ ] No stale windows remain open
- [ ] Text files remain unchanged as schema unless explicitly implemented
- [ ] Application restarts with demo data
- [ ] Error and success dialogs use consistent wording
- [ ] Console has no uncaught exceptions during normal workflows

## 9. Final Project Review

### Assessed score: 78/100

| Area | Score | Rationale |
|---|---:|---|
| Requirements and architecture | 18/20 | Strong service/file separation and role coverage |
| OOP implementation | 17/20 | Good class hierarchy and services; some UI integration remains |
| GUI and UX | 12/20 | Polished foundation but inconsistent placeholders, stale layouts and navigation gaps |
| Persistence/integration | 13/15 | Functional text-file architecture, with schema consistency defects |
| Testing | 7/15 | Testing is not visibly evidence-backed |
| Documentation | 8/10 | README and Word documentation exist, but are incomplete/out of sync |

### GUI weaknesses

- Placeholder buttons/dialogs undermine feature completeness.
- Dashboard and form surfaces use inconsistent light/dark standards.
- Several forms are fixed, dense or not verified for DPI/window resizing.
- `.form` files are stale and should not remain as authoritative evidence.
- Text-area/table mismatch and malformed demo records affect presentation.
- CRUD forms display operational data but no clear status/empty/result feedback.

### Missing documentation sections

- Requirements specification and traceability matrix
- Installation/run instructions with exact JDK/NetBeans version
- Text-file schemas and sample data
- Detailed use cases and role permissions
- GUI design rationale and design system
- Test cases and test evidence
- Known limitations and future work
- Deployment/user manual
- Repository structure and build instructions

### Missing screenshots

- Login
- Every role dashboard
- Administrator management
- Student management
- Payment records
- Scheduling
- Attendance
- Equipment/issues
- Ratings/bookings
- Success/error/confirmation dialogs
- Empty and no-search-result states

### Missing diagrams

- Use-case diagram
- Updated UML class diagram
- System architecture diagram
- Student/booking sequence diagram
- Authentication activity diagram
- CRUD sequence/activity diagrams
- File persistence diagram
- State diagram for booking/issue status

There is currently one `diagrams/UserHierarchy.png`; it is not enough for final documentation.

### Distinction improvements

1. Complete every visible navigation action; remove placeholders.
2. Standardize all screens on the supplied palette and typography.
3. Replace remaining read-only text views with typed, sortable tables.
4. Add live dashboard KPIs and at least one real report.
5. Fix text-file schema/record defects and include clean demo data.
6. Add 100-line regression checklist evidence with screenshots and pass/fail results.
7. Update README and Word documentation to match the actual application.
8. Add use-case, class, sequence and architecture diagrams.
9. Produce a concise user manual and assessor walkthrough.
10. Perform a 60-minute demo rehearsal and maintain a one-page demo script.

At 78/100, the project is viable for a pass/merit but needs visible polish and evidence for distinction. A focused GUI completion plus testing/documentation should realistically raise it to 88-92/100.

## 10. Week 6 Completion Plan

### Must Complete

1. Remove placeholder navigation and wire every dashboard button.
2. Apply the design system to Login and all dashboards.
3. Fix 1180x720 and 960x640 layout clipping.
4. Align Facilities equipment/issues table columns.
5. Repair malformed ratings data and generated demo data.
6. Add confirmation/error/success dialogs to CRUD actions.
7. Add search feedback and empty states.
8. Copy demo datasets to `data/` and run smoke tests by role.
9. Capture screenshots of final screens and dialogs.
10. Update README and documentation with run steps and schemas.

### Should Complete

1. Add typed sortable tables for Payments, Attendance, Ratings and Bookings.
2. Implement functional Staff and Reports views.
3. Add Instructor scheduling and rating/issue workflows to GUI.
4. Add student booking/rating/payment views.
5. Add filter fields and record counts.
6. Add use-case, sequence and architecture diagrams.
7. Produce test evidence and a requirements traceability table.
8. Add a demo script and backup of clean demo data.

### Nice To Have

1. Payment/booking charts.
2. CSV export.
3. Minor animation/highlight effects.
4. Theme toggle.
5. Audit/activity log.
6. Advanced report filters.

### Fastest one-week sequence

**Day 1:** Palette and shared layouts, dashboard navigation, clipping fixes.  
**Day 2:** Staff/report views, tables and CRUD dialogs.  
**Day 3:** Data schemas, demo files and role smoke tests.  
**Day 4:** Validation, empty states and confirmation dialogs.  
**Day 5:** Screenshots and diagrams.  
**Day 6:** Full test checklist and bug fixes.  
**Day 7:** README, documentation, demo rehearsal and submission packaging.
