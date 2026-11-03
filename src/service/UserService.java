package service;

import filehandling.FileManager;
import model.User;

import java.util.ArrayList;

public class UserService {

    public User login(String username, String password) {

        FileManager fileManager = new FileManager();

        ArrayList<User> users = fileManager.readUsers();

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                return user;
            }
        }

        return null;
    }
}