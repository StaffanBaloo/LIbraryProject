import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class NoteRepository extends Repository {

    public NoteRepository(){

    }

    public Note getById(int noteId){
        Note note;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT id, member_id, loan_id, type, message, sent_date, is_read
                FROM notes
                WHERE notes.id = ?
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
}
