public class UserService {
    UserRepository userRepository = new UserRepository();

    public User getById(int userId){
        return userRepository.getById(int userId);
    }

    public User getByEmail(String email){
        return userRepository.getByEmail(email);
    }

    public String getFullName(int userId){
        return getById(userId).getFullName();
    }

    public String getStatus(int userId){
        return getById(userId).getStatus();
    }

    public int getNumberUnreadNotes(int userId){
        NoteService noteService = new NoteService();
        return noteService.getNumberUnreadNotesByUser(userId);

    }

    public float getUnpaidFinesTotal(int userId){
        FineService fineService = new FineService();
        return fineService.getUnpaidFinesTotalByUser(userId);
    }

    public int getNumberOfCurrentLoans(int userId) {
        LoanService loanService = new LoanService();
        return loanService.getNumberOfCurrentLoansByUser(userId);
    }

    public int getNumberOfOverdueLoans(int userId) {
        LoanService loanService = new LoanService();
        return loanService.getNumberOfOverdueLoansByUser(userId);
    }

    
}
