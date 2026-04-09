package category;

import Repository;
import java.sql.*
import java.util.ArrayList;


public class CategoryRepository extends Repository {

    public ArrayList<Category> getCategoriesByBookId(int bookId) {
        ArrayList<Category> categories = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("""
                SELECT * FROM categories
                JOIN books_categories ON books_categories.category_id = categories.id
                WHERE books.categories.book_id = ?;
            """)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                categories.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return categories;
    }

    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categories;")){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                categories.add(mapRow(rs));
            }
        }catch (SQLException e) {
            System.out.println("Fel: " + e.getMessage());
        }
        return categories;
    }

    private Category mapRow(ResultSet rs) {
        return new Category(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"));
    }

}
