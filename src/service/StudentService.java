package service;

import java.util.Scanner;
import java.util.ArrayList;

import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;
import model.User;

public class StudentService {

    public boolean submitRating(String studentId, String instructorId,
            String rating) {
        if (studentId == null || studentId.trim().isEmpty()
                || instructorId == null || instructorId.trim().isEmpty()
                || rating == null || rating.trim().isEmpty()) {
            return false;
        }

        int value;
        try {
            value = Integer.parseInt(rating.trim());
        } catch (NumberFormatException ex) {
            return false;
        }
        if (value < 1 || value > 5) {
            return false;
        }

        FileManager fileManager = new FileManager();
        ArrayList<User> users = fileManager.readUsers();
        boolean studentExists = users.stream().anyMatch(user ->
                "Student".equals(user.getRole())
                && user.getUserId().equalsIgnoreCase(studentId.trim()));
        boolean instructorExists = users.stream().anyMatch(user ->
                ("Instructor".equals(user.getRole())
                || "Instructor".equals(user.getRole()))
                && user.getUserId().equalsIgnoreCase(instructorId.trim()));
        if (!studentExists || !instructorExists) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/ratings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3
                        && data[0].trim().equalsIgnoreCase(studentId.trim())
                        && data[1].trim().equalsIgnoreCase(instructorId.trim())) {
                    return false;
                }
            }
        } catch (Exception ignored) {
        }

        fileManager.saveRating(studentId.trim() + "," + instructorId.trim()
                + "," + value);
        return true;
    }

    public boolean addStudent(String studentId, String name,
            String username, String password) {

        if (studentId == null || studentId.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        FileManager fileManager = new FileManager();
        ArrayList<User> users = fileManager.readUsers();
        for (User user : users) {
            if (user.getUserId().equalsIgnoreCase(studentId.trim())
                    || user.getUsername().equalsIgnoreCase(username.trim())) {
                return false;
            }
        }

        String record =
                studentId.trim() + "," +
                username.trim() + "," +
                password + "," +
                name.trim() + "," +
                "Student";

        fileManager.saveUser(record);
        return true;
    }

    public boolean updateStudent(String studentId, String name,
            String username, String password) {

        if (studentId == null || studentId.trim().isEmpty()
                || name == null || name.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()) {
            return false;
        }

        FileManager fileManager = new FileManager();
        ArrayList<User> users = fileManager.readUsers();
        boolean found = false;

        for (User user : users) {
            if (!user.getUserId().equalsIgnoreCase(studentId.trim())
                    && user.getUsername().equalsIgnoreCase(username.trim())) {
                return false;
            }
            if (user.getRole().equals("Student")
                    && user.getUserId().equals(studentId.trim())) {
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);
                found = true;
                break;
            }
        }

        if (found) {
            fileManager.overwriteUsers(users);
        }

        return found;
    }

    public boolean deleteStudent(String studentId) {

        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }

        FileManager fileManager = new FileManager();
        ArrayList<User> users = fileManager.readUsers();
        boolean removed = users.removeIf(user
                -> user.getRole().equals("Student")
                && user.getUserId().equals(studentId.trim()));

        if (removed) {
            fileManager.overwriteUsers(users);
        }

        return removed;
    }

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
        
        if(studentId.isEmpty()
        || name.isEmpty()
        || username.isEmpty()
        || password.isEmpty()) {

        System.out.println(
            "All fields are required."
        );

        return;
}

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
    public void viewClasses() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/classes.txt"));

        String line;

        System.out.println(
                "\n===== AVAILABLE CLASSES ====="
        );

        while((line = br.readLine()) != null) {

            System.out.println(line);

        }

        br.close();

    } catch(Exception e) {

        System.out.println(
                "Error reading classes."
        );
    }
}
    public void bookClass() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== BOOK CLASS =====");

        System.out.print("Student ID or username: ");
        String studentId = scanner.nextLine();

        System.out.print("Class ID: ");
        String classId = scanner.nextLine();

        boolean booked = new BookingService().bookClass(studentId, classId);
        if (booked) {
            System.out.println("\nClass booked successfully!");
        } else {
            System.out.println(
                    "\nBooking failed. Check the student ID/username and class ID."
            );
        }
}
    public void submitRating() {

    Scanner scanner = new Scanner(System.in);

    System.out.println("\n===== SUBMIT RATING =====");

    System.out.print("Student ID: ");
    String studentId = scanner.nextLine();
    
    System.out.print("Instructor ID: ");
    String instructorId = scanner.nextLine();

    System.out.print("Rating (1-5): ");
    String rating = scanner.nextLine();

    String record = studentId + "," +
            instructorId + "," +
            rating;

    FileManager fileManager =
            new FileManager();

    fileManager.saveRating(record);

    System.out.println(
            "\nRating Submitted Successfully!"
    );
}
    public void viewBookings() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/bookings.txt"));

        String line;

        System.out.println(
        "\n===== BOOKINGS ====="
);

while ((line = br.readLine()) != null) {

    String[] data =
            line.split(",");

    System.out.println(
            "Student: " + data[0]
            + " | Class: " + data[1]
            + " | Status: " + data[2]
    );
}


        br.close();

    } catch (Exception e) {

        System.out.println(
                "Error reading bookings."
        );

        e.printStackTrace();
    }
}
    public void viewPayments() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/payments.txt"));

        String line;

        System.out.println(
                "\n===== PAYMENT HISTORY ====="
        );

        while ((line = br.readLine()) != null) {

            System.out.println(line);
        }

        br.close();

    } catch (Exception e) {

        System.out.println(
                "Error reading payments."
        );
    }
}

}