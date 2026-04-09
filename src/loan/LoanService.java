package loan;
import book.BookRepository;
import exceptions.LoanReturnException;
import fine.Fine;
import fine.FineService;
import member.MemberRepository;
import Rules;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class LoanService {

    LoanRepository loanRepository = new LoanRepository();
    FineService fineService = new FineService();
    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();

    public Loan getLoanById(int loanId){
        Loan loan = loanRepository.getLoanById(loanId);
        fillWithBook(loan);
        fillWithMember(loan);
        return loan;
    }

    public int getNumberOfCurrentLoansByMember(int memberId) {
        return loanRepository.getNumberOfCurrentLoansByMember(memberId);
    }

    public int getNumberOfOverdueLoansByMember(int memberId) {
        return loanRepository.getNumberOfOverdueLoansByMember(memberId);
    }

    public ArrayList<Loan> getCurrentLoansByMember(int memberId) {
        ArrayList<Loan> loans = loanRepository.getLoansByMember(memberId);
        ArrayList<Loan> loans2 = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getReturnDate() == null) {
                fillWithMember(loan);
                fillWithBook(loan);
                loans2.add(loan);
            }
        }
        return loans2;
    }

    public ArrayList<Loan> getAllLoansByMember(int memberId) {
        ArrayList<Loan> loans = loanRepository.getLoansByMember(memberId);
        for (Loan loan : loans) {
            fillWithMember(loan);
            fillWithBook(loan);
        }
        return loans;
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
                    fineService.createFine(new Fine(loan, newFine));
                }

            }  catch (LoanReturnException e) {
                System.out.println("Could not return loan "+loan.getId()+".");
                System.out.println(e.getMessage());
            }

        }
    }

    public Loan getLoanByFineId(int fineId) {
        Loan loan = loanRepository.getLoanByFineId(fineId);
        fillWithBook(loan);
        fillWithMember(loan);
        return loan;
    }

    public void fillWithBook (Loan loan) {
        loan.setBook(bookRepository.getBookByLoanId (loan.getId()));
    }

    public void fillWithMember(Loan loan) {
        loan.setMember(memberRepository.getMemberByLoanId(loan.getId()));
    }

    public int calculateFine(Loan loan){
        return Rules.fineByMembershipType(loan.getMember().getMembershipType() * weeksOverdue(loan);
    }

    public int weeksOverdue(Loan loan){
        return (int) Math.ceil(DAYS.between(loan.getDueDate(), LocalDate.now())/7);
    }
}
