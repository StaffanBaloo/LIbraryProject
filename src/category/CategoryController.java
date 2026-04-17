package category;

import exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;
import IO;

public class CategoryController {
    CategoryService categoryService = new CategoryService();
    Scanner scanner = new Scanner(System.in);
    public CategoryController() {
    }

    public void showLibrarianMenu(){
        boolean active = true;
        while (active){
            System.out.println("""
                    Welcome to the Category menu! Please choose an option:
                    1. Show all categories.
                    2. Show detailed category information.
                    3. Create new category.
                    4. Edit category.
                    5. Delete category.
                    0. Go back.""");
            int choice = IO.inputNumber();
            switch (choice) {
                case 1 -> showAllCategories();
                case 2 -> showCategoryInfo();
                case 3 -> createCategory();
                case 4 -> editCategory();
                case 5 -> deleteCategory();
                case 0 -> active = false;
                default -> System.out.println("Please enter a valid choice.");
            }
        }
    }

    public void showAllCategories(){
        ArrayList<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("There are no categories.");
        } else {
            System.out.println("ID | Category name");
            for (Category category : categories) {
                System.out.println(category.getId() + " | " + category.getName());
            }
        }
    }

    public void showCategoryInfo(){
        boolean active = true;
        while (active) {
            System.out.println("Please enter category ID (or 0 to go back):");
            int id = IO.inputNumber();
            if(id==0) {
                active = false;
            } else {
                if (categoryService.exists(id)) {
                    Category category = categoryService.getCategoryById(id);
                    System.out.println("Name: " + category.getName());
                    System.out.println("Description: " + category.getDescription());
                    active=false;
                } else {
                    System.out.println("There is no category with that ID.");
                }
            }
        }
    }

    public void editCategory(){
        boolean active = true;
        while(active){
            System.out.println("Please enter category ID (0 to go back):");
            int id = IO.inputNumber();
            if(id==0){
                active = false;
            } else if(id<1){
                System.out.println("ID has to be a positive number.");
            } else if(!categoryService.exists(id)) {
                System.out.println("No category with that ID exists.");
            } else {
                Category category = categoryService.getCategoryById(id);
                boolean active2 = true;
                while(active2) {
                    System.out.println("What information do you want to edit?");
                    System.out.println("1. Name: " + category.getName());
                    System.out.println("2. Description:");
                    System.out.println(category.getDescription());
                    System.out.println("9. Exit and save.");
                    System.out.println("0. Exit without saving.");
                    int choice = IO.inputNumber();
                    switch (choice){
                        case 1 -> category.setName(askForName());
                        case 2 -> category.setDescription(askForDescription());
                        case 9 -> {
                            try {
                                categoryService.save(category);
                                active2 = false;
                                active = false;
                                System.out.println("Category " + category.getName() + " saved.");
                            } catch (CantSaveCategoryException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 0 -> {
                            active2 = false;
                            active = false;
                        }
                    }
                }
            }
        }
    }

    public void createCategory(){
        String name = askForName();
        String description = askForDescription();
        Category category = new Category(name, description);
        try {
            categoryService.addCategory(category);
            System.out.println("Category successfully created with ID: " + category.getId());
        } catch (CantCreateCategoryException e) {
            System.out.println("Could not create the category "+category.getName()+".");
        }
    }

    public void deleteCategory(){
        boolean active = true;
        while(active){
            System.out.println("Please enter category ID (0 to go back):");
            int id = IO.inputNumber();
            if(id==0){
                active = false;
            } else if(id<1){
                System.out.println("ID has to be a positive number.");
            } else if(!categoryService.exists(id)) {
                System.out.println("No category with that ID exists.");
            } else {
                active = false;
                Category category = categoryService.getCategoryById(id);
                System.out.println("Name: " + category.getName());
                System.out.println("Description:");
                System.out.println(category.getDescription());
                System.out.println("Enter \"DELETE\" in all caps to delete category.");
                String choice = scanner.nextLine().trim();
                if(choice=="DELETE") {
                    try {
                        categoryService.delete(category);
                        System.out.println(category.getName() + " deleted.");
                    } catch (CantDeleteCategoryException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Category not deleted.");
                }
            }
        }
    }

    public String askForName(){
        boolean active = true;
        String name = "";
        while (active) {
            System.out.println("Please enter the category name.");
            name = scanner.nextLine().trim();
            if(name.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty name.");
            }
        }
        return name;
    }

    public String askForDescription(){
        boolean active = true;
        String description = "";
        while (active) {
            System.out.println("Please enter the category description.");
            description = scanner.nextLine().trim();
            if(description.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty description.");
            }
        }
        return description;
    }
}
