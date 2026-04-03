package Loan;
import Fine.FineService;
import Rules;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanService {

    LoanRepository loanRepository = new LoanRepository();
    FineService fineService = new FineService();

    public Loan getLoanById(int loanId){
        return loanRepository.getLoanById(loanId);
    }

    public int getNumberOfCurrentLoansByMember(int memberId) {
        return loanRepository.getNumberOfCurrentLoansByMember(memberId);
    }

    public int getNumberOfOverdueLoansByMember(int memberId) {
        return loanRepository.getNumberOfOverdueLoansByMember(memberId);
    }

    public ArrayList<Loan> getCurrentLoansByMember(int memberId) {
        return loanRepository.getCurrentLoansByMember(memberId);
    }

    public int returnLoan(Loan loan) {
        int fine = calculateFine(loan);
        try {
            loanRepository.returnLoan(loan);
            System.out.println("You have returned " + loan.getBook().getTitle() + ".");
            if (fine > 0) {
                System.out.println("You have incurred a new fee of " + fine + ".");
                fineService.createFine(loan, fine);
            }
            return fine;
        } catch (LoanReturnException e){
            System.out.println("Could not return loan "+loan.getId()+".");
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int returnAllLoansForMember(int memberId){
        ArrayList<Loan> loans = getCurrentLoansByMember(memberId);
        int totalFines=0;
        for(Loan loan:loans) {
            try {
                int newFine = returnLoan(loan);
                System.out.println("You have returned " + loan.getBook().getTitle() + ".");
                if (newFine>0){
                    fineService.createFine

                }

            }  catch (LoanReturnException e) {
                System.out.println("Could not return loan "+loan.getId()+".");
                System.out.println(e.getMessage());
            }

        }
    }



    int calculateFine(Loan loan){
        return Rules.feeByMembershipType(loan.getMember().getMembershipType() * weeksOverdue(loan);
    }

    int weeksOverdue(Loan loan){
        return (int) Math.ceil(DAYS.between(loan.getDueDate(), LocalDate.now())/7);
    }
}
