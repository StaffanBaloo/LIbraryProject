package loan;

import book.Book;
import mapper.LoanMapper;
import member.Member;

import java.util.ArrayList;
import java.util.Optional;

public class LoanListDTOService {
    LoanRepository loanRepository = new LoanRepository();
    LoanService loanService = new LoanService();


    public Optional<LoanListDTO> getLoanListDTOById(int loanId) {
        Optional<Loan> maybeLoan = loanService.getLoanById(loanId);
        if(maybeLoan.isPresent()){
            return Optional.of(LoanMapper.mapToLoanListDTO(maybeLoan.get()));
        } else return Optional.empty();
    }

    public ArrayList<LoanListDTO> getAllLoanListDTOs() {
        return LoanMapper.mapToLoanListDTOs(loanService.getAllLoans());
    }

    public ArrayList<LoanListDTO> getAllCurrentLoanListDTOs() {
        return LoanMapper.mapToLoanListDTOs(loanService.getAllCurrentLoans());
    }

    public ArrayList<LoanListDTO> getAllOverdueLoanListDTOs(){
        return LoanMapper.mapToLoanListDTOs(loanService.getAllOverdueLoans());
    }

    public ArrayList<LoanListDTO> getCurrentLoanListDTOsByMember(Member member) {
        return LoanMapper.mapToLoanListDTOs(loanService.getCurrentLoansByMember(member));
    }

    public ArrayList<LoanListDTO> getAllLoanListDTOsByMember(Member member) {
        return LoanMapper.mapToLoanListDTOs(loanService.getAllLoansByMember(member));
    }

    public ArrayList<LoanListDTO> getLoanListDTOsByBook (Book book) {
        return LoanMapper.mapToLoanListDTOs(loanService.getLoansByBook(book));
    }

}
