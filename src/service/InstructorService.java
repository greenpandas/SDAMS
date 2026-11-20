package service;

import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class InstructorService {

    private final FileManager fileManager = new FileManager();

    public boolean createSchedule(String classId, String className,
            String date, String time) {
        if (!areValidFields(classId, className, date, time)) {
            return false;
        }
        try {
            LocalDate.parse(date.trim());
            LocalTime.parse(time.trim());
        } catch (DateTimeParseException ex) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/classes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length > 0
                        && data[0].trim().equalsIgnoreCase(classId.trim())) {
                    return false;
                }
            }
        } catch (Exception ignored) {
        }
        return fileManager.saveClass(String.join(",",
                classId.trim(), className.trim(), date.trim(), time.trim()));
    }

    public boolean reportIssue(String issueId, String description) {
        if (!areValidFields(issueId, description)) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/issues.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length > 0
                        && data[0].trim().equalsIgnoreCase(issueId.trim())) {
                    return false;
                }
            }
        } catch (Exception ignored) {
        }
        return fileManager.saveIssue(String.join(",",
                issueId.trim(), description.trim()));
    }

    private boolean areValidFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty() || field.contains(",")) {
                return false;
            }
        }
        return true;
    }

    public void createSchedule() {

    Scanner scanner = new Scanner(System.in);

    System.out.println("\n===== CREATE SCHEDULE =====");

    System.out.print("Class ID: ");
    String classId = scanner.nextLine();

    System.out.print("Class Name: ");
    String className = scanner.nextLine();

    System.out.print("Date: ");
    String date = scanner.nextLine();

    System.out.print("Time: ");
    String time = scanner.nextLine();

    if (createSchedule(classId, className, date, time)) {
        System.out.println("\nSchedule Created Successfully!");
    } else {
        System.out.println("\nCould not save schedule. Check the field values.");
    }
}

    public void viewSchedule() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/classes.txt"));

        String line;

        System.out.println(
                "\n===== CLASS SCHEDULES ====="
        );

        while((line = br.readLine()) != null) {

            System.out.println(line);

        }

        br.close();

    } catch(Exception e) {

        System.out.println(
                "Error reading schedules."
        );
    }
}

    public void reportIssue() {

    Scanner scanner = new Scanner(System.in);

    System.out.println(
            "\n===== REPORT FACILITY ISSUE ====="
    );

    System.out.print("Issue ID: ");
    String issueId = scanner.nextLine();

    System.out.print("Issue Description: ");
    String description = scanner.nextLine();

    if (reportIssue(issueId, description)) {
        System.out.println("\nIssue Reported Successfully!");
    } else {
        System.out.println("\nCould not save issue. Check the field values.");
    }
}

    public void viewRatings() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/ratings.txt"));

        String line;

        System.out.println(
                "\n===== RATINGS ====="
        );

        while((line = br.readLine()) != null) {

            String[] data = line.split(",");

            System.out.println(
            "Student: " + data[0]
            + " | Instructor: " + data[1]
            + " | Rating: " + data[2]
);
        }

        br.close();

    } catch(Exception e) {

        System.out.println(
                "Error reading ratings."
        );
    }
}
}