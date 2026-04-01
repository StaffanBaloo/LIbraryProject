import org.apache.commons.validator.routines.EmailValidator;

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
            System.out.println("1. Guest?");
            System.out.println("2. User?");
            System.out.println("3. Administrator?");
            System.out.println("4. Test ANSI");
            System.out.println("0. Exit");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:{
                    IO.NYI();
                    break;
                }
                case 2:{
                    login();
                    UserController userController = new UserController;
                    userController.showMenu();
                    break;
                }
                case 3:{
                    /*AdminController adminController = new AdminController();
                    adminController.showMenu();*/
                    break;
                }
                case 4:{
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

    public int login(){
        boolean active = true;
        User user;
        UserService userService = new UserService();
        EmailValidator emailValidator = EmailValidator.getInstance();
        while(active){
            System.out.println("Please enter your member ID or your e-mail address:");
            String input=scanner.nextLine().trim();

            if (IO.isNumeric (input)){
                user = userService.getById(Integer.parseInt(input));
                active=false;
            } else if (emailValidator.isValid(input)) {
                user = userService.getByEmail(input);
                active=false;
            } else {
                System.out.println("Invalid ID or e-mail address.");
            }
        }
        return user;

    }



}
