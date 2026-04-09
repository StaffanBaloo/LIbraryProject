package fine;
import Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FineRepository extends Repository {

    public void createFine(Fine fine) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                INSERT INTO fines
                    (loan_id, amount, issued_date, status)
                VALUES (?, ?, ?, ?)
                """)) {
            stmt.setInt(1, fine.getLoan().getId());
            stmt.setInt(2, fine.getAmount());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setString(4, "pending");
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0){
                throw (FineCreationException ("Could not create fine for loan " + loan.getId()));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }

    public void payFine (Fine fine) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE fines SET paid_date = ?, status = 'paid' WHERE id = ?")) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, fine.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0){
                throw (FineCreationException ("Could not pay fine " + fine.getId()));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }

    public void cancelFine (Fine fine) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE fines SET paid_date = ?, status = 'cancelled' WHERE id = ?")) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, fine.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0){
                throw (FineCreationException ("Could not cancel fine " + fine.getId()));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }

    public ArrayList<Fine> getAllFines (){
        ArrayList<Fine> fines = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fines") {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                fines.add(new Fine(rs.getInt("id"),
                        rs.getInt("amount"),
                        rs.getDate("issues_date").toLocalDate(),
                        rs.getDate("paid_date").toLocalDate(),
                        rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return fines;
    }
}
