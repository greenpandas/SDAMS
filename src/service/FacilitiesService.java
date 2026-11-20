package service;

import java.util.Scanner;
import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;

public class FacilitiesService {

    public boolean addEquipment(String equipmentId, String equipmentName) {
        if (equipmentId == null || equipmentName == null
                || equipmentId.trim().isEmpty()
                || equipmentName.trim().isEmpty()
                || equipmentId.contains(",")
                || equipmentName.contains(",")) {
            return false;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/equipment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length > 0
                        && data[0].trim().equalsIgnoreCase(equipmentId.trim())) {
                    return false;
                }
            }
        } catch (Exception ignored) {
        }
        new FileManager().saveEquipment(equipmentId.trim()
                + "," + equipmentName.trim());
        return true;
    }

    public void addEquipment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD EQUIPMENT =====");

        System.out.print("Equipment ID: ");
        String equipmentId = scanner.nextLine();

        System.out.print("Equipment Name: ");
        String equipmentName = scanner.nextLine();
        
        if(equipmentId.isEmpty()
        || equipmentName.isEmpty()) {

        System.out.println(
            "All fields are required."
        );

        return;
}

        String record =
                equipmentId + "," +
                equipmentName;

        FileManager fileManager =
                new FileManager();

        fileManager.saveEquipment(record);

        System.out.println(
                "\nEquipment Added Successfully!"
        );
    }

    public void viewEquipment() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/equipment.txt"));

        String line;

        System.out.println(
                "\n===== EQUIPMENT LIST ====="
        );

        while ((line = br.readLine()) != null) {

            System.out.println(line);
        }

        br.close();

    } catch (Exception e) {

        System.out.println(
                "Error reading equipment."
        );
    }
}
    public void viewIssues() {

    try {

        BufferedReader br =
                new BufferedReader(
                new FileReader("data/issues.txt"));

        String line;

        System.out.println(
                "\n===== FACILITY ISSUES ====="
        );

        while ((line = br.readLine()) != null) {

            System.out.println(line);
        }

        br.close();

    } catch (Exception e) {

        System.out.println(
                "Error reading issues."
        );
    }
}
}