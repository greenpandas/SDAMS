package service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import filehandling.FileManager;



public class AttendanceService {

    public void recordAttendance() {

    Scanner scanner = new Scanner(System.in);

    System.out.println("\n===== RECORD ATTENDANCE =====");

    System.out.print("Student ID: ");
    String studentId = scanner.nextLine();

    System.out.print("Date: ");
    String date = scanner.nextLine();

    System.out.print("Status (Present/Absent): ");
    String status = scanner.nextLine();

    String record =
            studentId + "," +
            date + "," +
            status;

    FileManager fileManager =
            new FileManager();

    fileManager.saveAttendance(record);

    System.out.println(
            "\nAttendance Recorded Successfully!"
    );
}

    public void viewAttendance() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/attendance.txt"));

        String line;

        System.out.println(
                "\n===== ATTENDANCE RECORDS ====="
        );

        while ((line = br.readLine()) != null) {

            System.out.println(line);
        }

        br.close();

    } catch (Exception e) {

        System.out.println(
                "Error reading attendance."
        );
    }
}
}