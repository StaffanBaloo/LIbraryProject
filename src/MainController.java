import member.*;
import exceptions.*;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Scanner;

public class MainController {
    Scanner scanner = new Scanner(System.in);


    public void showMainMenu(){
        boolean active = true;

        while(active) {
            System.out.println("======Main Meny=====");
            System.out.println("Welcome to the library!");
            System.out.println("Are you a:");
            System.out.println("1. Guest?");
            System.out.println("2. Member?");
            System.out.println("3. Librarian?");
            System.out.println("4. Test ANSI");
            System.out.println("0. Exit");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> IO.NYI();
                case 2 -> {
                    login();
                    UserController userController = new UserController();
                    userController.showMenu();
                }
                case 3 ->{
                    LibrarianController librarianController = new LibrarianController();
                    librarianController.showMenu();
                }
                case 4 ->{
                    System.out.println(ANSI.bold()+ANSI.color("red") + "This should be bold red"+ANSI.reset());
                    System.out.println(ANSI.bold() + "bold " + ANSI.noBold() + ANSI.italic() + "italic " + ANSI.noItalic() + ANSI.underline() + "underline " + ANSI.noUnderline() + "default");
                    System.out.println(ANSI.color("bright_yellow") + "bright yellow " + ANSI.color("blue") + "blue " + ANSI.color("orange") + "orange");
                }
                case 0 -> active=false;
            }
        }
    }

    public void login(){
        boolean active = true;
        Member user = null;
        MemberService memberService = new MemberService();
        EmailValidator emailValidator = EmailValidator.getInstance();
        while(active){
            System.out.println("Please enter your member ID or your e-mail address:");
            String input=scanner.nextLine().trim();

            if (IO.isNumeric (input)){
                try {
                    user = memberService.getById(Integer.parseInt(input));
                    active = false;
                } catch (MemberNotFoundException e) {
                    System.out.println("Member " + input + " could not be found.");
                }
            } else if (emailValidator.isValid(input)) {
                try {
                    user = memberService.getByEmail(input);
                    active=false;
                } catch (MemberNotFoundException e) {
                    System.out.println("Could not find any member with the email "+input + ".");
                }

            } else {
                System.out.println("Invalid ID or e-mail address.");
            }
        }
        if(!(null == user)){
            Main.login(user);
        }
    }
}
