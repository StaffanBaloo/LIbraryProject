package member;

import book.BookController;
import loan.*;
import note.*;
import Main;

import java.util.Scanner;

public class UserController {
    MemberService memberService = new MemberService();
    LoanService loanService = new LoanService();
    NoteService noteService = new NoteService();
    Scanner scanner = new Scanner(System.in);

    public UserController() {

    }

    public void showMenu(){
        boolean active = true;
        int numberUnreadNotes, numberLoans, numberOverdueLoans, choice;
        float totalFines;

        while (active){
            System.out.println("Welcome, " + Main.loggedInUser.getFirstName() + "!");
            System.out.println("Your membership status is: " + Main.loggedInUser.getStatus() + ".");

            numberUnreadNotes = noteService.getNumberUnreadNotesByMember(Main.loggedInUser.getMemberId());
            if (numberUnreadNotes > 0) {
                System.out.println("You have " + numberUnreadNotes + " unread notifications.");
            } else {
                System.out.println("You have no unread notifications.");
            }

            totalFines = fineService.getUnpaidFinesTotalByMember(Main.loggedInUser.getMemberId());
            if (totalFines > 0f) {
                System.out.println("You have " + totalFines + " in unpaid fines.");
            }

            numberLoans = loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId());
            numberOverdueLoans = loanService.getNumberOfOverdueLoansByMember(Main.loggedInUser.getMemberId());
            if(numberLoans>0){
                if(numberOverdueLoans>0) {
                    System.out.println("You have " + numberLoans + " loans currently, of which " + numberOverdueLoans + " are overdue.");
                } else {
                    System.out.println("You have " + numberLoans + " loans currently.");
                }
            } else {
                System.out.println("You do not currently have any loans.");
            }
            System.out.println("""
                    What do you want to do?
                    1. Book menu.
                    2. Loans menu.
                    3. Read notifications.
                    0. Go back.""");
            choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: {
                    BookController bookController = new BookController();
                    bookController.showMenu();
                    break;
                }
                case 2: {
                    LoanController loanController = new LoanController();
                    loanController.showMenu();
                    break;
                }
                case 3: {
                    NoteController noteController = new NoteController();
                    noteController.showMenu();
                    break;
                }
                case 0: {
                    active=false;
                    break;
                }
            }
        }
    }
}
