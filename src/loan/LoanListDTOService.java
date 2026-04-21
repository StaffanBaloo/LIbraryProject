package loan;

import book.Book;
import mapper.LoanMapper;
import member.Member;

import java.util.ArrayList;

public class LoanListDTOService {
    LoanRepository loanRepository = new LoanRepository();
    LoanService loanService = new LoanService();


    public LoanListDTO getLoanListDTOById(int loanId) {
        return LoanMapper.mapToLoanListDTO(loanService.getLoanById(loanId));
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
