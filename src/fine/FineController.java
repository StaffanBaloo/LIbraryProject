package fine;

import IO;
import Main;
import member.Member;

import java.util.ArrayList;

public class FineController {
    FineService fineService = new FineService();
    public FineController() {
    }

    public void showMenu(){
        boolean active = true;
        while(active){
            System.out.println("""
                    What do you want to do?
                    1. View all my unpaid fines.
                    2. Pay a fine.
                    0. Go back.""");
            int choice = IO.inputNumber();
            switch (choice) {
                case 1 -> showUnpaidFines(Main.loggedInUser);
                case 2 -> payFine(Main.loggedInUser);
                case 0 -> active = false;
                default -> System.out.println("Please enter a valid choice.");
            }
        }
    }

    public void showUnpaidFines(Member member){
        ArrayList<Fine> fines = fineService.getAllUnpaidFinesForMember(member);
        if(fines.isEmpty()) {
            System.out.println("You have no unpaid fines.");
        } else {
            System.out.println("ID | Amount | Title");
            for (Fine fine : fines) {
                System.out.println(fine.getId() + " | " + fine.getAmount() + " kr | " + fine.getLoan().getBook().getTitle());
            }
        }
    }

    public void payFine(Member member) {
        boolean active = true;
        while(active) {
            System.out.println("Please enter fine ID (or 0 to go back):");
            int id = IO.inputNumber();
            if(id==0) {
                active = false;
            } else if (fineService.exists(id)){
                Fine fine = fineService.getFineById(id);
                if(member.getMemberId()==fine.getLoan().getMember().getMemberId()) {
                    fineService.payFine(fine);
                    active = false;
                } else {
                    System.out.println("That fine does not belong to one of your loans.");
                }
            } else {
                System.out.println("There is no fine with ID "+id+".");
            }
        }
    }
}
