package category;
import java.util.ArrayList;
import java.util.Optional;

public class CategoryService {
    CategoryRepository categoryRepository = new CategoryRepository();

    public ArrayList<Category> getAllCategories(){
        return categoryRepository.getAllCategories();
    }

    public ArrayList<Category> getCategoriesByPartialName(String name) {
        return categoryRepository.getCategoriesByPartialName(name);
    }

    public Optional<Category> getCategoryById(int categoryId){
        return categoryRepository.getCategoryById(categoryId);
    }

    public boolean exists(int categoryId) {
        return categoryRepository.exists(categoryId);
    }

    public void addCategory(Category category){
        categoryRepository.addCategory(category);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public  void delete(Category category){
        categoryRepository.delete(category);
    }
}
