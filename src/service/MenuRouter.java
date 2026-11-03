package service;

import model.User;
import java.util.Scanner;
import service.ManagerService;
import service.AdminService;

public class MenuRouter {

    public void route(User user) {
        

        switch(user.getRole()) {

            case "Manager":
                showManagerMenu();
                break;

            case "Administrator":
                showAdminMenu();
                break;

            case "Instructor":
                showInstructorMenu();
                break;

            case "Student":
                showStudentMenu();
                break;

            default:
                System.out.println("Unknown role.");
        }
    }

    private void showManagerMenu() {

    Scanner scanner = new Scanner(System.in);
    ManagerService managerService = new ManagerService();

   

    boolean running = true;

while(running)
{
    System.out.println("\n===== MANAGER MENU =====");
    System.out.println("1. Manage Administrators");
    System.out.println("2. View Staff");
    System.out.println("3. Staff Reports");
    System.out.println("4. Revenue Reports");
    System.out.println("5. Logout");

    System.out.print("\nEnter choice: ");

    int choice = scanner.nextInt();

    switch(choice)
    {
        case 1:
            managerService.manageAdministrators();
            break;

        case 2:
            managerService.viewStaff();
            break;

        case 3:
            managerService.generateStaffReport();
            break;

        case 4:
            managerService.generateRevenueReport();
            break;

        case 5:
            running = false;
            System.out.println("Logged out.");
            break;

        default:
            System.out.println("Invalid choice.");
    }
}
}

    private void showAdminMenu() {

    Scanner scanner = new Scanner(System.in);

    AdminService adminService =
            new AdminService();

    boolean running = true;

    while(running) {

        System.out.println("\n===== ADMIN MENU =====");

        System.out.println("1. Manage Students");
        System.out.println("2. Manage Staff");
        System.out.println("3. Payment Records");
        System.out.println("4. Logout");

        System.out.print("\nEnter choice: ");

        int choice = scanner.nextInt();

        switch(choice) {

            case 1:
                adminService.manageStudents();
                break;

            case 2:
                adminService.manageStaff();
                break;

            case 3:
                adminService.managePayments();
                break;

            case 4:
                running = false;
                break;

            default:
                System.out.println("Invalid Choice.");
        }
    }
}

    private void showInstructorMenu() {

    Scanner scanner = new Scanner(System.in);

    InstructorService instructorService =
            new InstructorService();

    boolean running = true;

    while (running) {

        System.out.println("\n===== INSTRUCTOR MENU =====");

        System.out.println("1. Create Schedule");
        System.out.println("2. View Schedule");
        System.out.println("3. Report Facility Issue");
        System.out.println("4. View Ratings");
        System.out.println("5. Logout");

        System.out.print("\nEnter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                instructorService.createSchedule();
                break;

            case 2:
                instructorService.viewSchedule();
                break;

            case 3:
                instructorService.reportIssue();
                break;

            case 4:
                instructorService.viewRatings();
                break;

            case 5:
                running = false;
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}

    private void showStudentMenu() {

    Scanner scanner = new Scanner(System.in);

    StudentService studentService =
            new StudentService();

    boolean running = true;

    while(running) {

        System.out.println("\n===== STUDENT MENU =====");

        System.out.println("1. View Classes");
        System.out.println("2. Book Class");
        System.out.println("3. Logout");

        System.out.print("\nEnter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {

            case 1:
                studentService.viewClasses();
                break;

            case 2:
                studentService.bookClass();
                break;

            case 3:
                running = false;
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}
}