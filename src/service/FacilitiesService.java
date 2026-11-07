package service;

import java.util.Scanner;
import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;

public class FacilitiesService {

    public void addEquipment() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ADD EQUIPMENT =====");

        System.out.print("Equipment ID: ");
        String equipmentId = scanner.nextLine();

        System.out.print("Equipment Name: ");
        String equipmentName = scanner.nextLine();

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
}