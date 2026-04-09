package note;

import Repository;

import java.sql.*;

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
                note = mapRow(rs);
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

    private Note mapRow(ResultSet rs) {
        return new Note(rs.getInt("id"),
                rs.getString("type"),
                rs.getString("message"),
                rs.getDate("sent_date").toLocalDate(),
                rs.getBoolean("is_read"));
    }
}
