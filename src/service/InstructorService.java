package service;

import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class InstructorService {

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

    FileManager fileManager =
            new FileManager();

    String record =
            classId + "," +
            className + "," +
            date + "," +
            time;

    fileManager.saveClass(record);

    System.out.println(
            "\nSchedule Created Successfully!"
    );
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

    String record =
            issueId + "," +
            description;

    FileManager fileManager =
            new FileManager();

    fileManager.saveIssue(record);

    System.out.println(
            "\nIssue Reported Successfully!"
    );
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