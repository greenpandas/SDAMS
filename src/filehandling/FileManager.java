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
    public void saveClass(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/classes.txt", true);

        fw.write(record + "\n");

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving class.");
        e.printStackTrace();
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
    public void saveIssue(String record) {

    try {

        FileWriter fw =
                new FileWriter("data/issues.txt", true);

        fw.write(record + "\n");

        fw.flush();

        fw.close();

    } catch (Exception e) {

        System.out.println("Error saving issue.");
        e.printStackTrace();
    }
}
}
