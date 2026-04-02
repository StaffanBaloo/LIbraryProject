package Member;

import Note.NoteService;

import java.util.ArrayList;

public class MemberService {

    MemberRepository memberRepository = new MemberRepository();

    public ArrayList<Member> getAllMembers(){
            return memberRepository.getAllMembers();
    }

    public Member getById(int memberId){
        return memberRepository.getById(memberId);
    }

    public Member getByEmail(String email){
        return memberRepository.getByEmail(email);
    }

    public int getNumberUnreadNotes(int memberId){
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
