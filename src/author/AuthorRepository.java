package author;

import Repository;
import java.sql.*;
import java.util.ArrayList;

public class AuthorRepository extends Repository {

    public ArrayList<Author> getAuthorsByBookId(int bookId) {
        ArrayList<Author> authors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT * FROM authors a
                JOIN books_authors ba ON ba.author_id = a.id
                JOIN author_descriptions  ad ON a.id = ad.author_id
                WHERE books_authors.book_id = ?;
            """)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return authors;
    }

    public Author getAuthorById(int authorId) {
        Author author;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT * FROM authors a
                JOIN author_descriptions  ad ON a.id = ad.author_id
                WHERE a.id = ?;
            """)) {
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                author = mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return author;
    }

    private Author mapRow(ResultSet rs) {
        return new Author (new Author(rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("nationality"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getString("ad.biography"),
                rs.getString("ad.website")));
    }

}
