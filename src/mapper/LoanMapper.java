package mapper;

import loan.LoanListDTO;
import loan.Loan;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LoanMapper {

    public static LoanListDTO mapToLoanListDTO(Loan loan){
        return new LoanListDTO(loan.getId(), BookMapper.maptoBookLoanDTO(loan.getBook()), loan.getMember(), loan.getLoanDate(), loan.getDueDate(), loan.getReturnDate());
    }

    public static ArrayList<LoanListDTO> mapToLoanListDTOs(ArrayList<Loan> loans) {
        return loans.stream()
                .map(LoanMapper::mapToLoanListDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
