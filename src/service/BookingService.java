/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import filehandling.FileManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import model.User;

public class BookingService {

    public boolean bookClass(String studentId, String classId) {
        if (studentId == null || classId == null
                || studentId.trim().isEmpty() || classId.trim().isEmpty()) {
            return false;
        }
        studentId = studentId.trim();
        classId = classId.trim();

        ArrayList<User> users = new FileManager().readUsers();
        boolean studentExists = users.stream().anyMatch(user
                -> "Student".equals(user.getRole())
                && user.getUserId().equalsIgnoreCase(studentId));
        if (!studentExists) {
            return false;
        }

        boolean classExists = false;
        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/classes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length > 0
                        && data[0].trim().equalsIgnoreCase(classId)) {
                    classExists = true;
                    break;
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        if (!classExists) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader("data/bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3
                        && data[0].trim().equalsIgnoreCase(studentId)
                        && data[1].trim().equalsIgnoreCase(classId)) {
                    return false;
                }
            }
        } catch (Exception ignored) {
        }

        new FileManager().saveBooking(studentId + "," + classId + ",Booked");
        return true;
    }
}
