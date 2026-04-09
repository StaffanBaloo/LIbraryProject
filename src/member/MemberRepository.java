package member;

import java.sql.*;
import java.util.ArrayList;

import Repository;

public class MemberRepository extends Repository {

    public MemberRepository () {

    }

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> members = new ArrayList<>();
        // try-with-resources stänger anslutningen automatiskt när blocket är klart
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // executeQuery() skickar en SELECT-fråga och returnerar ett ResultSet
            ResultSet rs = stmt.executeQuery("""
                SELECT * from members;
            """);

            // rs.next() går till nästa rad — returnerar false när det inte finns fler
            while (rs.next()) {
                members.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return members;
    }

    public member getByEmail(String mail){
        Member member;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.createStatement("SELECT * from members WHERE email=? ")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.first()) {
                member = mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return member;
    }

    public member getById(int memberId){
        Member member;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.createStatement("SELECT * from members WHERE id=? ")) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.first()) {
                member = mapRow(rs);
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return member;
    }

    private Member mapRow(ResultSet rs) {
        return new Member(rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("membership_type"),
                rs.getString("status"),
                rs.getDate("membership_date").toLocalDate());
    }


}
