package service;

import java.util.Scanner;
import java.util.ArrayList;

import filehandling.FileManager;
import model.User;

public class StudentService {

    public void addStudent() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD STUDENT =====");

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        FileManager fileManager = new FileManager();

        String record =
                studentId + "," +
                username + "," +
                password + "," +
                name + "," +
                "Student";

        fileManager.saveUser(record);

        System.out.println("\nStudent Added Successfully!");
    }

    public void viewStudents() {

        FileManager fileManager = new FileManager();

        ArrayList<User> users = fileManager.readUsers();

        System.out.println("\n===== STUDENT LIST =====");

        for (User user : users) {

            if (user.getRole().equals("Student")) {

                System.out.println(
                        user.getUserId()
                        + " | "
                        + user.getName()
                        + " | "
                        + user.getUsername()
                );
            }
        }
    }

    public void searchStudent() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter Student ID: ");

        String searchId = scanner.nextLine();

        FileManager fileManager = new FileManager();

        ArrayList<User> users = fileManager.readUsers();

        boolean found = false;

        for (User user : users) {

            if (user.getRole().equals("Student")
                    && user.getUserId().equals(searchId)) {

                System.out.println("\nStudent Found:");

                System.out.println(
                        user.getUserId()
                        + " | "
                        + user.getName()
                        + " | "
                        + user.getUsername()
                );

                found = true;
                break;
            }
        }

        if (!found) {

            System.out.println("Student not found.");
        }
    }

    public void updateStudent() {
        Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter Student ID to update: ");
    String adminId = scanner.nextLine();

    FileManager fileManager = new FileManager();

    ArrayList<User> users = fileManager.readUsers();

    boolean found = false;

    for (User user : users) {

        if (user.getRole().equals("Student")
                && user.getUserId().equals(adminId)) {

            System.out.print("New Name: ");
            String newName = scanner.nextLine();

            System.out.print("New Username: ");
            String newUsername = scanner.nextLine();

            System.out.print("New Password: ");
            String newPassword = scanner.nextLine();

            user.setName(newName);
            user.setUsername(newUsername);
            user.setPassword(newPassword);

            found = true;

            break;
        }
    }

    if (found) {

        fileManager.overwriteUsers(users);

        System.out.println(
                "Student updated successfully."
        );

    } else {

        System.out.println(
                "Student not found."
        );
    }
    }

    public void deleteStudent() {
        Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter Student ID to delete: ");

    String adminId = scanner.nextLine();

    FileManager fileManager = new FileManager();

    ArrayList<User> users =
            fileManager.readUsers();

    boolean removed = false;

    for (int i = 0; i < users.size(); i++) {

        User user = users.get(i);

        if (user.getRole().equals("Student")
                && user.getUserId().equals(adminId)) {

            users.remove(i);

            removed = true;

            break;
        }
    }

    if (removed) {

        fileManager.overwriteUsers(users);

        System.out.println(
                "Student deleted successfully."
        );

    } else {

        System.out.println(
                "Student not found."
        );
    }
    }
}