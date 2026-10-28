package service;

import java.util.Scanner;
import filehandling.FileManager;
import model.User;
import java.util.ArrayList;

public class ManagerService {

    public void addAdministrator() {
        

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD ADMINISTRATOR =====");

        System.out.print("Admin ID: ");
        String adminId = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        FileManager fileManager = new FileManager();

        String record =
                adminId + "," +
                username + "," +
                password + "," +
                name + "," +
                "Administrator";

        fileManager.saveUser(record);

        System.out.println("\nAdministrator Added Successfully!");
    }

    public void viewAdministrators() {

    FileManager fileManager = new FileManager();

    ArrayList<User> users = fileManager.readUsers();

    System.out.println("\n===== ADMINISTRATOR LIST =====");

    for (User user : users) {

        if (user.getRole().equals("Administrator")) {

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

    public void searchAdministrator() {

    Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter Admin ID: ");

    String searchId = scanner.nextLine();

    FileManager fileManager = new FileManager();

    ArrayList<User> users = fileManager.readUsers();

    boolean found = false;

    for (User user : users) {

        if (user.getRole().equals("Administrator")
                && user.getUserId().equals(searchId)) {

            System.out.println("\nAdministrator Found:");
            System.out.println(
                    user.getUserId()
                    + " | "
                    + user.getName()
                    + " | "
                    + user.getUsername()
            );

            found = true;
        }
    }

    if (!found) {

        System.out.println("Administrator not found.");
    }
}

    public void updateAdministrator() {

    Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter Admin ID to update: ");
    String adminId = scanner.nextLine();

    FileManager fileManager = new FileManager();

    ArrayList<User> users = fileManager.readUsers();

    boolean found = false;

    for (User user : users) {

        if (user.getRole().equals("Administrator")
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
                "Administrator updated successfully."
        );

    } else {

        System.out.println(
                "Administrator not found."
        );
    }
}

    public void deleteAdministrator() {

    Scanner scanner = new Scanner(System.in);

    System.out.print("\nEnter Admin ID to delete: ");

    String adminId = scanner.nextLine();

    FileManager fileManager = new FileManager();

    ArrayList<User> users =
            fileManager.readUsers();

    boolean removed = false;

    for (int i = 0; i < users.size(); i++) {

        User user = users.get(i);

        if (user.getRole().equals("Administrator")
                && user.getUserId().equals(adminId)) {

            users.remove(i);

            removed = true;

            break;
        }
    }

    if (removed) {

        fileManager.overwriteUsers(users);

        System.out.println(
                "Administrator deleted successfully."
        );

    } else {

        System.out.println(
                "Administrator not found."
        );
    }
}

    public void manageAdministrators() {

    Scanner scanner = new Scanner(System.in);

    boolean running = true;

    while (running) {

        System.out.println("\n===== ADMINISTRATOR MANAGEMENT =====");

        System.out.println("1. Add Administrator");
        System.out.println("2. View Administrators");
        System.out.println("3. Search Administrator");
        System.out.println("4. Update Administrator");
        System.out.println("5. Delete Administrator");
        System.out.println("6. Back");

        System.out.print("\nEnter choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                addAdministrator();
                break;

            case 2:
                viewAdministrators();
                break;

            case 3:
                searchAdministrator();
                break;

            case 4:
                updateAdministrator();
                break;

            case 5:
                deleteAdministrator();
                break;

            case 6:
                running = false;
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}

    public void viewStaff() {
        System.out.println("View Staff module.");
    }

    public void generateStaffReport() {
        System.out.println("Staff Report module.");
    }

    public void generateRevenueReport() {
        System.out.println("Revenue Report module.");
    }
}