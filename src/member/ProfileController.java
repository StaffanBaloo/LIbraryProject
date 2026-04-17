package member;
import Main;
import fine.FineController;
import fine.FineService;
import loan.LoanService;
import note.NoteController;
import note.NoteService;

import java.util.Scanner;

public class ProfileController () {
    NoteService noteService = new NoteService();
    FineService fineService = new FineService();
    LoanService loanService = new LoanService();
    Scanner scanner = new Scanner(System.in);


    public ProfileController (){

    }

    public void showMenu(){
        boolean active = true;
        int numberUnreadNotes, numberLoans, numberOverdueLoans, choice;
        int totalFines;

        while (active){
            System.out.println("Welcome, " + Main.loggedInUser.getFirstName() + "!");
            System.out.println("Your membership status is: " + Main.loggedInUser.getStatus() + ".");

            numberUnreadNotes = noteService.getNumberUnreadNotesByMember(Main.loggedInUser);
            if (numberUnreadNotes > 0) {
                System.out.println("You have " + numberUnreadNotes + " unread notifications.");
            } else {
                System.out.println("You have no unread notifications.");
            }

            totalFines = fineService.getUnpaidFinesTotalByMemberId(Main.loggedInUser.getMemberId());
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
                    1. Notifications menu.
                    2. Fines menu.
                    3. Membership menu.
                    0. Go back.""");
            choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    NoteController noteController = new NoteController();
                    noteController.showMenu();
                }
                case 2 -> {
                    FineController fineController = new FineController();
                    fineController.showMenu();
                }
                case 3 -> {
                    MemberController memberController = new MemberController();
                    memberController.showMenu();
                }
                case 0 -> active = false;
            }

        }
    }
}
