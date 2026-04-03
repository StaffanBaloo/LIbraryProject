package Fine;

import Loan.Loan;

public class FineService {
    FineRepository fineRepository = new FineRepository();

    public void createFine(Loan loan, int amount) {
        fineRepository.createFine(new Fine(loan, amount));
    }

    public void payFine (Fine fine) {
        fineRepository.payFine(fine);
    }

}
