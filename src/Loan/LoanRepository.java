package Loan;

import Book.BookLoanDTO;
import Member.Member;
import Repository;

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
                SELECT *, books.title, m.*
                FROM loans
                JOIN books on books.id = loans.book_id
                JOIN members m om members.id = loans.member_id
                WHERE member_id = ?
                    AND return_date IS NULL""")){
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loans.add(new Loan(
                        rs.getInt("id"),
                        new BookLoanDTO(rs.getInt("book_id"),
                                rs.getString("books.title")),
                        new Member(rs.getInt("member_id"),
                                rs.getString("m.first_name"),
                                rs.getString("m.last_name"),
                                rs.getString("m.email"),
                                rs.getString("m.membership_type"),
                                rs.getString("m.status"),
                                rs.getDate("m.membership_date").toLocalDate()),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate());
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return loans;
    }

    public ArrayList<Loan> getAllLoansByMember(int memberId) {
        ArrayList<Loan> loans;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT *, books.title, m.*
                FROM loans
                JOIN books on books.id = loans.book_id
                JOIN members m om members.id = loans.member_id
                WHERE member_id = ?""")){
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                loans.add(new Loan(
                        rs.getInt("id"),
                        new BookLoanDTO(rs.getInt("book_id"),
                                rs.getString("books.title")),
                        new Member(rs.getInt("member_id"),
                                rs.getString("m.first_name"),
                                rs.getString("m.last_name"),
                                rs.getString("m.email"),
                                rs.getString("m.membership_type"),
                                rs.getString("m.status"),
                                rs.getDate("m.membership_date").toLocalDate()),
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
                        new BookLoanDTO(rs.getInt("book_id"),
                                rs.getString("books.title")),
                        new Member(rs.getInt("member_id"),
                                rs.getString("m.first_name"),
                                rs.getString("m.last_name"),
                                rs.getString("m.email"),
                                rs.getString("m.membership_type"),
                                rs.getString("m.status"),
                                rs.getDate("m.membership_date").toLocalDate()),
                        rs.getDate("loan_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate());
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
}
