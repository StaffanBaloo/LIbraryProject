package fine;

import loan.Loan;

import java.util.ArrayList;

public class FineService {
    FineRepository fineRepository = new FineRepository();

    public void createFine(Loan loan, int amount) {
        fineRepository.createFine(new Fine(loan, amount));
    }

    public void payFine (Fine fine) {
        fineRepository.payFine(fine);
    }

    public ArrayList<Fine> getAllFines(){
        ArrayList<Fine> fines = fineRepository.getAllFines();
        for(Fine fine : fines) {

        }

    }

}
