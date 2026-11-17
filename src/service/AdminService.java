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
    
    // Saves new student to users.txt, loads error message if something is wrong
public String addStudent(String studentId, String name,
        String username, String password) {

// checks for commas
    if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
        return "All fields are required.";
    }

    if (name.contains(",") || username.contains(",") || password.contains(",")) {
        return "Fields cannot contain commas.";
    }

    FileManager fileManager = new FileManager();

// checks for mathcing usernames or IDs
    for (User user : fileManager.readUsers()) {
        if (user.getUsername().equals(username)) {
            return "Username already exists.";
        }
        if (user.getUserId().equals(studentId)) {
            return "Student ID already exists.";
        }
    }

    String record =
            studentId + "," +
            username + "," +
            password + "," +
            name + "," +
            "Student";

    //adds new student
    fileManager.saveUser(record);

    return null;
}

    
public String generateStudentId() {

    FileManager fileManager = new FileManager();
    int max = 0;

    // Reads through users.txt and ignores the non-student users
    for (User user : fileManager.readUsers()) {

        if (user.getRole().equals("Student")) {
            try {
                int num = Integer.parseInt(user.getUserId().substring(1));
                if (num > max) {
                    max = num;
                }
            } catch (Exception e) {
            }
        }
    }

    return String.format("S%03d", max + 1);
}

//filter users by role
public ArrayList<User> getUsersByRole(String role) {

    FileManager fileManager = new FileManager();
    ArrayList<User> filteredUsers = new ArrayList<>();

    for (User user : fileManager.readUsers()) {

        if (role.equalsIgnoreCase(user.getRole())) {
            filteredUsers.add(user);
        }
    }

        return filteredUsers;
    }

// search function for users
public ArrayList<User> searchUsers(String keyword, String userType) {
    FileManager fileManager = new FileManager();
    ArrayList<User> matchingUsers = new ArrayList<>();

    keyword = (keyword == null) ? "" : keyword.trim().toLowerCase();

    for (User user : fileManager.readUsers()) {
        boolean isStudent = user.getRole().equalsIgnoreCase("Student");

        boolean correctType;

        if (userType.equalsIgnoreCase("Student")) {
            correctType = isStudent;
        } else if (userType.equalsIgnoreCase("Staff")) {
            correctType = !isStudent;
        } else {
            correctType = false;
        }

        if (!correctType) {
            continue;
        }

        boolean matches =
                user.getUserId().toLowerCase().contains(keyword)
                || user.getUsername().toLowerCase().contains(keyword)
                || user.getPassword().toLowerCase().contains(keyword)
                || user.getName().toLowerCase().contains(keyword)
                || user.getRole().toLowerCase().contains(keyword);

        if (matches) {
            matchingUsers.add(user);
        }
    }

    return matchingUsers;
}
}