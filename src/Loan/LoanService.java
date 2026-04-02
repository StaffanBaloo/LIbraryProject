package Loan;
import Book.*;
import Member.MemberService;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanService {

    LoanRepository loanRepository = new loanRepository();

    public int getNumberOfCurrentLoansByMember(int memberId) {
        return loanRepository.getNumberOfCurrentLoansByMember(memberId);
    }

    public int getNumberOfOverdueLoansByMember(int memberId) {
        return loanRepository.getNumberOfOverdueLoansByMember(memberId);
    }

    public ArrayList<Loan> getCurrentLoansByMember(int memberId) {
        return loanRepository.getCurrentLoansByMember(memberId);
    }

    public float returnLoan(int memberId, int loanId) {
        Loan loan = loanRepository.getLoanById(loanId);

        return loanRepository.returnLoan(memberId, loanId);
    }

    public float returnAllLoansForMember(int memberId){
        ArrayList<Loan> loans = getCurrentLoansByMember(memberId);
        float totalFees=0;
        for(Loan loan:loans) {
            try {
                int newFee = returnLoan(memberId, loan.getId());
                System.out.println("You have returned " + loanService.getBookTitleByLoanId(loanId) + ".");

            }  catch (LoanRenewException e) {
                System.out.println("Could not return loan "+loan.getId()+".");
                System.out.println(e.getMessage());
            }

        }
    }

    float calculateFee(Loan loan){
        MemberService memberService = new MemberService();
        return Rules.feeByMembershipType(memberService.getById(loan.getMemberId()).getMembershipType()) * weeksOverdue(loan);
    }

    int weeksOverdue(Loan loan){
        return Math.ceil(DAYS.between(loan.getDueDate(), LocalDate.now())/7)
    }
}
