package filehandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
}
