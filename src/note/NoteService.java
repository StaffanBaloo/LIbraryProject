package note;

import loan.LoanRepository;
import member.MemberRepository;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

public class NoteService {
    NoteRepository noteRepository = new NoteRepository();
    LoanRepository loanRepository = new LoanRepository();
    MemberRepository memberRepository = new MemberRepository();

    public Note getById(int noteId){
        Note note = noteRepository.getById(noteId);
        fillWithLoan(note);
        fillWithMember(note);
    }

    public int getNumberUnreadNotesByMember(int userId){
        return noteRepository.getNumberUnreadNotesByMember(userId);

    }

    public int getNumberNotesByUser(int userId){
        return noteRepository.getNumberNotesByMember(userId);
    }

    public void markRead(int noteId){
        noteRepository.markRead(noteId);
    }

    public void markUnread(int noteId){
        noteRepository.markUnread(noteId);
    }

    public Note getNote(int noteId){
        return noteRepository.getNote(noteId);
    }

    public Note getOldestUnreadByUser(int userId){
        return noteRepository.getOldestUnreadByMember(userId);
    }

    public ArrayList<Note> getNotesByUser(int userId) {
        return noteRepository.getNotesByMember(userId);
    }

    public ArrayList<Note> getUnreadNotesByUser(int userId) {
        ArrayList<Note> notes = noteRepository.getNotesByUser(userId)
                .stream()
                .filter(!(Note::isRead()))
                .collect(Collectors.toList());

    }

    public void fillWithLoan(Note note) {
        note.setLoan(loanRepository);
    }

}
