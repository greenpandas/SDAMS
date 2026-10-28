package service;

import model.User;
import java.util.Scanner;
import service.ManagerService;

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
        System.out.println("\n===== ADMIN MENU =====");
    }

    private void showInstructorMenu() {
        System.out.println("\n===== INSTRUCTOR MENU =====");
    }

    private void showStudentMenu() {
        System.out.println("\n===== STUDENT MENU =====");
    }
}