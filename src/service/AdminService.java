package service;

import java.util.Scanner;

public class AdminService {

    public void manageStudents() {

        Scanner scanner = new Scanner(System.in);

        StudentService studentService =
                new StudentService();

        boolean running = true;

        while (running) {

            System.out.println("\n===== STUDENT MANAGEMENT =====");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back");

            System.out.print("\nEnter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    studentService.addStudent();
                    break;

                case 2:
                    studentService.viewStudents();
                    break;

                case 3:
                    studentService.searchStudent();
                    break;

                case 4:
                    studentService.updateStudent();
                    break;

                case 5:
                    studentService.deleteStudent();
                    break;

                case 6:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void manageStaff() {
        System.out.println("Staff Management Module");
    }

    public void managePayments() {
        System.out.println("Payment Records Module");
    }
}
