package filehandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.PrintWriter;

import model.User;

public class FileManager {

    public ArrayList<User> readUsers() {
        
        
     
        ArrayList<User> users = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(
                    new FileReader("data/users.txt"));

            String line;

            while ((line = br.readLine()) != null) {
                
                String[] data = line.split(",");
                

                User user = new User(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

                users.add(user);
            }

            br.close();

        } catch (Exception e) {

            System.out.println("Error reading users file.");
            e.printStackTrace();
        }
        
        
        
        return users;
        
    }
    public void saveUser(String record) {

    try {

        
        System.out.println(record);
        
        FileWriter fw =
                new FileWriter("data/users.txt", true);

        fw.write(record + "\n");

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving user.");
        e.printStackTrace();
    }
    
}
    


    public void overwriteUsers(ArrayList<User> users) {

    try {

        PrintWriter writer =
                new PrintWriter("data/users.txt");

        for (User user : users) {

            String record =
                    user.getUserId() + "," +
                    user.getUsername() + "," +
                    user.getPassword() + "," +
                    user.getName() + "," +
                    user.getRole();

            writer.println(record);
        }

        writer.close();

    } catch (Exception e) {

        System.out.println("Error updating users file.");
        e.printStackTrace();
    }
}
    public boolean saveClass(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/classes.txt", true);

        fw.write(record + "\n");

        fw.close();
        return true;

    } catch (Exception e) {

        System.out.println("Error saving class.");
        e.printStackTrace();
        return false;
    }
    
    
}
    public void saveAttendance(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/attendance.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving attendance.");
        e.printStackTrace();
    }
}
    public void saveEquipment(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/equipment.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving equipment.");
        e.printStackTrace();
    }
}
    public void saveBooking(String record) {

    try {

        

        System.out.println(
                "Record = " + record
        );

        System.out.println(
                new java.io.File("data/bookings.txt")
                        .getAbsolutePath()
        );

        FileWriter fw =
                new FileWriter("data/bookings.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

        System.out.println(
                "Booking saved."
        );

    } catch (Exception e) {

        System.out.println("Error saving booking.");
        e.printStackTrace();
    }
}
    public boolean updateBookingStatus(String studentId, String classId,
            String status) {
        if (studentId == null || studentId.trim().isEmpty()
                || classId == null || classId.trim().isEmpty()
                || status == null || status.trim().isEmpty()) {
            return false;
        }

        java.io.File source = new java.io.File("data/bookings.txt");
        ArrayList<String> updatedLines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(
                new java.io.FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 3
                        && data[0].trim().equalsIgnoreCase(studentId.trim())
                        && data[1].trim().equalsIgnoreCase(classId.trim())
                        && "Booked".equalsIgnoreCase(data[2].trim())) {
                    updatedLines.add(studentId.trim() + "," + classId.trim()
                            + "," + status.trim());
                    updated = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (Exception ex) {
            return false;
        }

        if (!updated) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(source, false))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
            return !writer.checkError();
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean writePaymentsCsv(ArrayList<String[]> payments,
            String outputPath) {
        if (payments == null || outputPath == null
                || outputPath.trim().isEmpty()) {
            return false;
        }

        java.io.File outputFile = new java.io.File(outputPath);
        java.io.File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(
                new java.io.OutputStreamWriter(
                        new java.io.FileOutputStream(outputFile),
                        java.nio.charset.StandardCharsets.UTF_8))) {
            writer.println("Student ID,Amount,Date");
            for (String[] payment : payments) {
                if (payment == null || payment.length < 3) {
                    return false;
                }
                writer.print(payment[0].replace(",", " "));
                writer.print(",");
                writer.print(payment[1].replace(",", " "));
                writer.print(",");
                writer.println(payment[2].replace(",", " "));
            }
            return !writer.checkError();
        } catch (Exception ex) {
            return false;
        }
    }
    public void saveRating(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/ratings.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving rating.");
        e.printStackTrace();
    }
}
    public boolean saveIssue(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/issues.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();
        return true;

    } catch (Exception e) {

        System.out.println("Error saving issue.");
        e.printStackTrace();
        return false;
    }
}
    public void savePayment(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/payments.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving payment.");
        e.printStackTrace();
    }
}
}
