package Loan;

import java.sql.*;
import java.util.ArrayList;

public class LoanRepository extends Repository {

    public int getNumberOfCurrentLoansByMember(int memberId){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS number FROM loans WHERE member_id=? AND return_date IS NULL")){
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()){
                return rs.getInt("number");
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return 0;

    }

    public int getNumberOfOverdueLoansByMember(int memberId){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT COUNT(*) AS number
                FROM loans
                WHERE member_id=?
                  AND return_date IS NULL
                  AND due_date<CURRENT_DATE()""")) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()){
                return rs.getInt("number");
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return 0;
    }

    public ArrayList<Loan> getCurrentLoansByMember(int memberId) {
        ArrayList<Loan> loans;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT *
                FROM loans
                WHERE member_id = ?
                    AND return_date IS NULL""")){
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return loans;
    }

    public Loan getLoanById(int loanId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * from loans WHERE id=?")) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if(rs.first()){
                return new Loan(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getInt("member_id"),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate()),
                        rs.getDate("return_date").toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }
}
