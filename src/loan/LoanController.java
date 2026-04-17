package loan;

import java.time.LocalDate;
import java.util.ArrayList;
import Main;
import IO;
import ANSI;
import exceptions.*;
import member.Member;

public class LoanController {
    LoanService loanService = new LoanService();

    public LoanController() {

    }

    public void showMenu(){
        boolean active = true;
        while(active) {
            System.out.println("""
                    Loans menu:
                    1. Show current loans.
                    2. Return all books.
                    3. Return one book.
                    4. Renew all loans.
                    5. Renew one loan.
                    0. Back.""");
            int choice=IO.inputNumber();
            switch (choice){
                case 1 -> showCurrentLoans(Main.loggedInUser);
                case 2 -> returnAllLoans(Main.loggedInUser);
                case 3 -> returnLoan(Main.loggedInUser);
                case 4 -> renewAllLoans(Main.loggedInUser);
                case 5 -> renewLoan(Main.loggedInUser);
                case 0 -> active = false;
            }
        }
    }

    public void showLibrarianMenu(){
        boolean active = true;
        while(active) {
            System.out.println("""
                    Loans menu:
                    1. Show all current loans.
                    2. Show all overdue loans.
                    3. Year top 10.
                    0. Back.""");
            int choice = IO.inputNumber();
            switch (choice) {
                case 1 -> showAllCurrentLoans();
                case 2 -> showAllOverdueLoans();
                case 3 -> showTopList(10, LocalDate.now().minusYears(1), LocalDate.now());
                case 0 -> active =false;
                default -> System.out.println("Please choose a valid option.");
            }
        }
    }

    public void showCurrentLoans(Member member){
        if (loanService.getNumberOfCurrentLoansByMember(member) >0) {
            System.out.println("Your current loans are:");
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(member);
            for (Loan loan : loans) {
                System.out.println(loan.toString());
            }
        }else {
            System.out.println("You do not have any current loans.");
        }
    }

    public void showAllCurrentLoans(){
        ArrayList<Loan> loans = loanService.getAllCurrentLoans();
        System.out.println("ID | Title | Loan date | Due date");
        for(Loan loan : loans){
            if(loan.isOverdue()){
                System.out.println(ANSI.color("bright_red") + loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getLoanDate() + " | " + loan.getDueDate() + ANSI.reset());
            } else {
                System.out.println(loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getLoanDate() + " | " + loan.getDueDate());
            }
        }
    }

    public void showAllOverdueLoans(){
        ArrayList<Loan> loans = loanService.getAllOverdueLoans();
        System.out.println("ID | Title | M.ID | Member name | Loan date | Due date");
        for(Loan loan : loans){
            System.out.println(loan.getId() + " | " + loan.getBook().getTitle() + " | " + loan.getMember().getMemberId() + " | " + loan.getMember().getFullName() + loan.getLoanDate() + " | " + loan.getDueDate());
        }
    }

    public void showTopList(int length, LocalDate start, LocalDate end){
        ArrayList<String> toptitles = loanService.topList(length, start, end);
        int counter = 0;
        if(toptitles.isEmpty()){
            System.out.println("There are no loans in that interval.");
        } else {
            System.out.println("Pos. | Title");
            for (String title : toptitles) {
                counter++;
                System.out.println(counter + " | " + title);
            }
        }
    }

    void returnAllLoans(Member member){
        if (loanService.getNumberOfCurrentLoansByMember(member) >0) {
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(member);
            int totalFees=0;
            int returnCounter=0;
            for(Loan loan:loans) {
                try {
                    int newFee = loanService.returnLoan(loan);
                    returnCounter++;
                    if (newFee>0){
                        totalFees+=newFee;
                    }
                }  catch (LoanRenewException e) {
                    System.out.println("Could not return loan "+loan.getId()+".");
                    System.out.println(e.getMessage());
                }

            }
            int loansRemaining = loanService.getNumberOfCurrentLoansByMember(member);
            if(loansRemaining==0){
                System.out.println("You have returned all your loans.");
            } else {
                System.out.println("You have returned " + returnCounter + " loans.");
            }

            if (totalFees > 0) {
                System.out.println("You have incurred new fees of " + totalFees + " kr.");
            }
        }
        else {
            System.out.println("You do not have any loans to return.");
        }
    }

    void returnLoan(Member member){
        if (loanService.getNumberOfCurrentLoansByMember(member) >0) {
            System.out.println("Which loan do you wish to return?");
            int loanId = IO.inputNumber();
            try {
                Loan loan = loanService.getLoanById(loanId);
                int newFee = loanService.returnLoan(loan);
                System.out.println("You have returned " + loan.getBook().getTitle() + ".");
                if(newFee>0){
                    System.out.println("You have incurred a new fee of " + newFee + " kr.");
                }
            } catch (LoanReturnException e) {
                System.out.println("Could not return that book.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("You do not have any loans to return.");
        }
    }

    void renewAllLoans(Member member){
        if (loanService.getNumberOfCurrentLoansByMember(member) >0){
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(member);
            for(Loan loan : loans){
                try{
                    loanService.renewLoan(loan);
                }
                catch (LoanRenewException e){
                    System.out.println("Could not renew loan " + loan.getId() + " of " + loan.getBook().getTitle() + ".");
                    System.out.println(e.getMessage());
                }
            }
            showCurrentLoans(member);
        } else {
            System.out.println("You do not have any loans to renew.");
        }
    }

    void renewLoan(Member member){
        if (loanService.getNumberOfCurrentLoansByMember(member) >0) {
            System.out.println("Which loan do you wish to renew?");
            int loanId = IO.inputNumber();
            try {
                Loan loan = loanService.getLoanById(loanId);
                loanService.renewLoan(loan);
                System.out.println("You have renewed your loan of " + loan.getBook().getTitle() + ".");
                System.out.println("The new due date is " + loanService.getLoanById(loanId).getDueDate() +".");
            } catch (LoanRenewException e) {
                System.out.println("Could not renew that loan.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("You do not have any loans to renew.");
        }
    }
}
