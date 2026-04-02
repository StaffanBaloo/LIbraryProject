package Book;

import java.sql.*;
import java.util.ArrayList;

public class BookRepository extends Repository {

    public BookRepository() {
    }

    public ArrayList<String> getAuthorsById(int id){
        ArrayList<String>  authorList = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT a.first_name, a.last_name
                FROM authors a
                JOIN books_authors ba on a.id = ba.author_id
                WHERE ba.book_id = ?
            """)) {
            stmt.setInt(1, id);
            ResultSet rs =stmt.executeQuery();
            while(rs.next()){
                authorList.add(rs.getString("last_name") + ", " + rs.getString("first_name"));
            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return authorList;


    }
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        // try-with-resources stänger anslutningen automatiskt när blocket är klart
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement()){

            // executeQuery() skickar en SELECT-fråga och returnerar ett ResultSet
            ResultSet rs = stmt.executeQuery("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id;
            """);

            // rs.next() går till nästa rad — returnerar false när det inte finns fler
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);

            }

        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }
    public ArrayList<Book> getBooksByTitle(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    WHERE title LIKE ?;
            """)) {
            stmt.setString(1, "%"+searchTerm+"%");
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }

    public ArrayList<Book> getBooksByAuthorId(int authorId) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    JOIN book_authors ba ON b.id = ba.book_id
                    WHERE ba.author_id = ?;
            """)) {
            stmt.setInt(1, authorId);
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }

    public ArrayList<Book> getBooksByAuthorName(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    JOIN book_authors ba ON b.id = ba.book_id
                    JOIN authors a ON ba.author_id = a.id
                    WHERE a.first_name LIKE ? OR a.last_name LIKE ?;
            """)) {
            stmt.setString(1, "%"+searchTerm+"%");
            stmt.setString(2, "%"+searchTerm+"%");
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }

    public ArrayList<Book> getBooksByCategoryId(int categoryId) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    JOIN book_categories bc ON b.id = bc.book_id
                    WHERE bc.category_id = ?;
            """)) {
            stmt.setInt(1, categoryId);
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }

    public ArrayList<Book> getBooksByCategory(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    JOIN book_categories bc ON b.id = bc.book_id
                    JOIN categories c ON bc.category_id = a.id
                    WHERE c.name LIKE ?;
            """)) {
            stmt.setString(1, "%"+searchTerm+"%");
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }

    public ArrayList<Book> getBooksByKeyword(String searchTerm) {
        ArrayList<Book> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT b.id AS id, title, year_published, available_copies, bd.summary, bd.page_count, bd.language
                    FROM books b 
                    JOIN book_descriptions bd ON b.id = bd.book_id
                    WHERE bd.summary LIKE ?;
            """)) {
            stmt.setString(1, "%"+searchTerm+"%");
            ResultSet rs =stmt.executeQuery();
            while(rs.next()) {
                int bookId = rs.getInt("id");
                String title = rs.getString("title");
                int yearPublished = rs.getInt("year_published");
                int availableCopies = rs.getInt("available_copies");
                ArrayList<String> authorList = getAuthorsById(rs.getInt("id"));
                String summary =rs.getString("summary");
                int pageCount = rs.getInt("page_count");
                String language = rs.getString("language");


                Book book = new Book(bookId, title, yearPublished, availableCopies, authorList, summary, pageCount, language);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return books;
    }
}
