package service;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import filehandling.FileManager;

public class PaymentService {

    private static final String PAYMENTS_FILE = "data/payments.txt";

    public boolean addPayment(String studentId, String amount, String date) {
        if (studentId == null || studentId.trim().isEmpty()
                || amount == null || amount.trim().isEmpty()
                || date == null || date.trim().isEmpty()) {
            return false;
        }

        try {
            BigDecimal value = new BigDecimal(amount.trim());
            if (value.compareTo(BigDecimal.ZERO) <= 0) {
                return false;
            }
            if (value.scale() > 2 && value.stripTrailingZeros().scale() > 2) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        try {
            LocalDate.parse(date.trim());
        } catch (DateTimeParseException ex) {
            return false;
        }
        boolean studentExists = new FileManager().readUsers().stream()
                .anyMatch(user -> "Student".equals(user.getRole())
                        && user.getUserId().equalsIgnoreCase(studentId.trim()));
        if (!studentExists) {
            return false;
        }

        new FileManager().savePayment(
                studentId.trim() + "," + amount.trim() + "," + date.trim());
        return true;
    }

    public ArrayList<String[]> readPayments() {
        ArrayList<String[]> payments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(PAYMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3) {
                    payments.add(new String[]{
                        data[0].trim(), data[1].trim(), data[2].trim()
                    });
                }
            }
        } catch (Exception ex) {
            return payments;
        }

        return payments;
    }

    public boolean exportPaymentsCsv() {
        return exportPaymentsCsv("reports/payments_report.csv");
    }

    public boolean exportPaymentsCsv(String outputPath) {
        if (outputPath == null || outputPath.trim().isEmpty()) {
            return false;
        }

        ArrayList<String[]> payments = readPayments();
        if (payments.isEmpty()) {
            return false;
        }

        for (String[] payment : payments) {
            if (payment == null || payment.length < 3
                    || payment[0].trim().isEmpty()
                    || payment[1].trim().isEmpty()
                    || payment[2].trim().isEmpty()) {
                return false;
            }
        }

        return new FileManager().writePaymentsCsv(payments, outputPath.trim());
    }

    public boolean deletePayment(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }

        ArrayList<String[]> payments = readPayments();
        boolean removed = false;
        ArrayList<String[]> remaining = new ArrayList<>();
        for (String[] payment : payments) {
            if (payment[0].equals(studentId.trim())) {
                removed = true;
            } else {
                remaining.add(payment);
            }
        }

        if (!removed) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(PAYMENTS_FILE, false))) {
            for (String[] payment : remaining) {
                writer.println(payment[0] + "," + payment[1] + "," + payment[2]);
            }
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

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