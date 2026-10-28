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
}
