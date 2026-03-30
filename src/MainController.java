import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;
import java.util.Scanner;

public class MainController {
    Scanner scanner = new Scanner(System.in);


    public void showMainMenu(){
        boolean active = true;
        int userId;

        while(active) {
            System.out.println("======Main Meny=====");
            System.out.println("Welcome to the library!");
            System.out.println("Are you a:");
            System.out.println("1. User?");
            System.out.println("2. Administrator?");
            System.out.println("3. Test ANSI");
            System.out.println("0. Exit");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:{
                    userId = findUser();
                    UserController userController = new UserController(userId);
                    userController.showMenu();
                    break;
                }
                case 2:{
                    AdminController adminController = new AdminController();
                    adminController.showMenu();
                    break;
                }
                case 3:{
                    System.out.println(ANSI.bold()+ANSI.color("red") + "This should be bold red"+ANSI.reset());
                    System.out.println(ANSI.bold() + "bold " + ANSI.noBold() + ANSI.italic() + "italic " + ANSI.noItalic() + ANSI.underline() + "underline " + ANSI.noUnderline() + "default");
                    System.out.println(ANSI.color("bright_yellow") + "bright yellow " + ANSI.color("blue") + "blue " + ANSI.color("orange") + "orange");
                }
                case 0:{
                    active=false;
                    break;
                }
            }
        }
    }

    public User findUser(){
        boolean active = true;
        User user;
        UserService userService = new UserService();
        EmailValidator emailValidator = EmailValidator.getInstance();
        while(active){
            System.out.println("Please enter your member ID or your e-mail address:");
            String input=scanner.nextLine();

            if (isNumeric (input)){
                user = userService.getById(Integer.parseInt(input));
            } else if (emailValidator.isValid(input)) {
                user = userService.getByEmail(input);
            }
            if (Objects.isNull(user)){
                System.out.println("Invalid ID or e-mail address.");
            }
            else {
                return user;
            }
        }

    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }


}
