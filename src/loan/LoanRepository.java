package loan;

import Repository;

import java.sql.*;
import java.time.LocalDate;
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

    public ArrayList<Loan> getLoansByMember(int memberId) {
        ArrayList<Loan> loans;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT *, m.*, b
                FROM loans
                WHERE member_id = ?""")){
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loans.add(mapRow(rs));
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
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return null;
    }

    public void returnLoan(Loan loan) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                UPDATE loans
                SET return_date=CURRENT_DATE()
                WHERE id=?;
                """)) {
            stmt.setInt(1, loan.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0){
                throw (LoanReturnException ("Could not renew loan " + loan.getId()));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }

    public void renewLoan(Loan loan, LocalDate newDate) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                UPDATE loans
                SET due_date=?
                WHERE id=?;
                """)) {
            stmt.setDate(1, Date.valueOf(newDate));
            stmt.setInt(2, loan.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0){
                throw (LoanReturnException ("Could not renew loan " + loan.getId()));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
    }

    public Loan getLoanByFineId(int fineId) {
        Loan loan;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT * FROM loans l
                JOIN fines f ON f.loan_id = l.id
                WHERE f.id=?;
                """)) {
            stmt.setInt(1, fineId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                loan = mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return loan;
    }

    private Loan mapRow (ResultSet rs) {
        return new Loan(rs.getInt("id"),
                rs.getDate("loan_date").toLocalDate(),
                rs.getDate("due_date").toLocalDate(),
                rs.getDate("return_date").toLocalDate());
    }
}
