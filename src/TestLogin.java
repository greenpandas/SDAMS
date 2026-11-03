import java.util.Scanner;

import model.User;
import service.UserService;
import service.MenuRouter;

public class TestLogin {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user != null) {

    System.out.println("\nLogin Successful!");
    System.out.println("Welcome " + user.getName());
    System.out.println("Role: " + user.getRole());
    MenuRouter router = new MenuRouter(); 
    router.route(user);

    

    } else {

    System.out.println("Invalid username or password");

}
    }
}