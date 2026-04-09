package note;

import loan.Loan;
import member.Member;

import java.time.LocalDate;

public class Note {
    private int noteId;
    private Member member;
    private Loan loan;
    private String type;
    private String message;
    private LocalDate sentDate;
    private boolean isRead;

    public Note(int noteId, Member member, Loan loan, String type, String message, LocalDate sentDate, boolean isRead) {
        this.noteId = noteId;
        this.member = member;
        this.loan = loan;
        this.type = type;
        this.message = message;
        this.sentDate = sentDate;
        this.isRead = isRead;
    }

    public Note(int noteId, String type, String message, LocalDate sentDate, boolean isRead) {
        this.noteId = noteId;
        this.type = type;
        this.message = message;
        this.sentDate = sentDate;
        this.isRead = isRead;
    }

    public String toString(){
        String temp = Integer.toString(noteId);
        temp+=" | " + type;
        temp+=" | " + sentDate.toString() + " | ";
        if(message.length()>20) {
            temp+=message.substring(0,19);
        } else {
            temp+=message;
        }
        return temp;

    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void markRead() {
        isRead = true;
    }

    public void markUnread(){
        isRead = false;
    }
}
