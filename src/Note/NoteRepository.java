package Note;

import Member.Member;
import Repository;

import java.sql.*;
import java.time.LocalDate;

public class NoteRepository extends Repository {

    public NoteRepository(){

    }

    public Note getById(int noteId){
        Note note;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT id, member_id, loan_id, type, message, sent_date, is_read
                FROM notifications
                WHERE id = ?
            """)) {
            stmt.setInt(1, noteId);
            ResultSet rs =stmt.executeQuery();
            if(rs.first()){
                int noteId = rs.getInt("id");
                int memberId = rs.getInt("member_id");
                int loanId = rs.getInt("loan_id");
                String type = rs.getString("type");
                String message = rs.getString("message");
                LocalDate sentDate = rs.getDate("sent_date").toLocalDate();
                boolean isRead = rs.getBoolean("is_read");
                Member member = memberService.getById(memberId);
                Loan loan = loanService.getById(loanId);
                note = new Note(noteId, member, loan, type, message, sentDate, isRead);
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return note;
    }

    public integer getNumberUnreadNotesByMember((int memberId){
        Note note;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT COUNT(*) as number
                FROM notifications
                WHERE member_id = ? AND is_read = false;
            """)) {
            stmt.setInt(1, memberId);
            ResultSet rs =stmt.executeQuery();
            if(rs.first()){
                return rs.getInt("number");
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }
}
