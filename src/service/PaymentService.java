package service;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

import filehandling.FileManager;

public class PaymentService {

    public void addPayment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD PAYMENT =====");

        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Amount: ");
        String amount = scanner.nextLine();
        
        System.out.print("Date: ");
        String date = scanner.nextLine();

        String record =
        studentId + "," +
        amount + "," +
        date;

        FileManager fileManager =
                new FileManager();

        fileManager.savePayment(record);

        System.out.println(
                "\nPayment Added Successfully!"
        );
    }

    public void viewPayments() {

        try {

            BufferedReader br =
                    new BufferedReader(
                    new FileReader("data/payments.txt"));

            String line;

            System.out.println(
                    "\n===== PAYMENT RECORDS ====="
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