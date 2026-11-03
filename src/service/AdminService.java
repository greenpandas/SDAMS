package service;

import java.util.Scanner;
import java.util.ArrayList;

import filehandling.FileManager;
import model.User;

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

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println("\n===== STAFF MANAGEMENT =====");

            System.out.println("1. View Staff");
            System.out.println("2. Search Staff");
            System.out.println("3. Back");

            System.out.print("\nEnter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    viewStaff();
                    break;

                case 2:
                    searchStaff();
                    break;

                case 3:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void viewStaff() {

        FileManager fileManager =
                new FileManager();

        ArrayList<User> users =
                fileManager.readUsers();

        System.out.println("\n===== STAFF LIST =====");

        for (User user : users) {

            if (!user.getRole().equals("Student")) {

                System.out.println(
                        user.getUserId()
                        + " | "
                        + user.getName()
                        + " | "
                        + user.getRole()
                );
            }
        }
    }

    public void searchStaff() {

        Scanner scanner =
                new Scanner(System.in);

        System.out.print("\nEnter Staff ID: ");

        String staffId =
                scanner.nextLine();

        FileManager fileManager =
                new FileManager();

        ArrayList<User> users =
                fileManager.readUsers();

        boolean found = false;

        for (User user : users) {

            if (!user.getRole().equals("Student")
                    && user.getUserId().equals(staffId)) {

                System.out.println("\nStaff Found:");

                System.out.println(
                        user.getUserId()
                        + " | "
                        + user.getName()
                        + " | "
                        + user.getRole()
                );

                found = true;
                break;
            }
        }

        if (!found) {

            System.out.println("Staff member not found.");
        }
    }

    public void managePayments() {

    Scanner scanner = new Scanner(System.in);

    PaymentService paymentService =
            new PaymentService();

    boolean running = true;

    while(running) {

        System.out.println("\n===== PAYMENT RECORDS =====");

        System.out.println("1. View Payments");
        System.out.println("2. Add Payment");
        System.out.println("3. Back");

        System.out.print("\nEnter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {

            case 1:
                paymentService.viewPayments();
                break;

            case 2:
                paymentService.addPayment();
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