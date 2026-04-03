package Loan;

import java.util.ArrayList;
import java.util.Scanner;
import Main;

public class LoanController {
    LoanService loanService = new LoanService();
    Scanner scanner = new Scanner(System.in);

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
            int choice=Integer.parseInt(scanner.nextLine().trim());
            switch (choice){
                case 1: {
                    showCurrentLoans();
                    break;
                }
                case 2: {
                    returnAllLoans();
                    break;
                }
                case 3: {
                    returnLoan();
                    break;
                }
                case 4: {
                    renewAllLoans();
                    break;
                }
                case 5:{
                    renewLoan();
                    break;
                }
                case 0: {
                    active = false;
                    break;
                }
            }
        }
    }
    void showCurrentLoans(){
        if (loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId()) >0) {
            System.out.println("Your current loans are:");
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(Main.loggedInUser.getMemberId());
            for (Loan loan : loans) {
                System.out.println(loan.toString());
            }
        }else {
            System.out.println("You do not have any current loans.");
        }
    }

    void returnAllLoans(){
        if (loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId()) >0) {
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(Main.loggedInUser.getMemberId());
            float totalFees=0;
            int returnCounter=0
            for(Loan loan:loans) {
                try {
                    int newFee = returnLoan(loan);
                    System.out.println("You have returned " + loan.getBook().getTitle() + ".");
                    returnCounter++;
                    if (newFee>0){
                        System.out.println("You have incurred a new fee of " + newFee + ".");
                        totalFees+=newFee;
                    }
                }  catch (LoanRenewException e) {
                    System.out.println("Could not return loan "+loan.getId()+".");
                    System.out.println(e.getMessage());
                }

            }
            int loansRemaining = loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId())
            if(loansRemaining=0){
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

    void returnLoan(){
        if (loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId()) >0) {
            System.out.println("Which loan do you wish to return?");
            int loanId = Integer.parseInt(scanner.nextLine());
            try {
                Loan loan = loanService.getLoanById(loanId);
                float newFee = loanService.returnLoan(loan);
                System.out.println("You have returned " + loan.getBook().getTitle() + ".");
                if (newFee > 0) {
                    System.out.println("You have incurred a new fee of " + newFee + ".");
                }
            } catch (LoanReturnException e) {
                System.out.println("Could not return that book.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("You do not have any loans to return.");
        }
    }
    void renewAllLoans(){
        if (loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId()) >0){
            ArrayList<Loan> loans = loanService.getCurrentLoansByMember(Main.loggedInUser.getMemberId());
            for(Loan loan : loans){
                try{
                    loanService.renewLoan(loan);
                }
                catch (LoanRenewException e){
                    System.out.println("Could not renew loan " + loan.getId() + " of " + loan.getBook().getTitle()) + ".");
                    System.out.println(e.getMessage());
                }
            }
            showCurrentLoans();
        } else {
            System.out.println("You do not have any loans to renew.");
        }
    }

    void renewLoan(){
        if (loanService.getNumberOfCurrentLoansByMember(Main.loggedInUser.getMemberId()) >0) {
            System.out.println("Which loan do you wish to renew?");
            int loanId = Integer.parseInt(scanner.nextLine());
            try {
                Loan loan = loanService.getLoanById(loanId);
                loanService.renewLoan(loan);
                System.out.println("You have renewed " + loan.getBook().getTitle()) + ".");
                System.out.println("The new due date is " + loanService.getDueDate(loanId) +".");
            } catch (LoanRenewException e) {
                System.out.println("Could not renew that loan.");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("You do not have any loans to renew.");
        }
    }
}
