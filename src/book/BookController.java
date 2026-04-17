package book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import IO;
import Main;
import author.*;
import category.*;
import loan.*;
import exceptions.*;

public class BookController {
    BookService bookService = new BookService();
    BookListDTOService bookListDTOService = new BookListDTOService();
    LoanService loanService = new LoanService();
    AuthorService authorService = new AuthorService();
    CategoryService categoryService = new CategoryService();
    Scanner scanner = new Scanner(System.in);

    public BookController() {

    }

    public void showMenu(){
        boolean active = true;

        while (active) {
            System.out.println("""
                    Book menu:
                    1. Display all books.
                    2. Display all available books.
                    3. Search books by author.
                    4. Search books by title.
                    5. Search books by category.
                    6. Search books by keyword.
                    7. Display detailed book info by ID.
                    8. Borrow book.
                    0. Back.""");
            int choice=Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> showAllBooks();
                case 2 -> showAvailableBooks();
                case 3 -> findByAuthor();
                case 4 -> findByTitle();
                case 5 -> findByCategory();
                case 6 -> findByKeyword();
                case 7 -> showDetailedInfo();
                case 8 -> borrowBook();
                case 0 -> active = false;
            }
        }
    }

    public void showLibrarianMenu() {
        boolean active = true;
        int choice;
        while (active) {
            System.out.println("""
                    Welcome, librarian!
                    1. Display all books.
                    2. Display all available books.
                    3. Search books by author.
                    4. Search books by title.
                    5. Search books by category.
                    6. Search books by keyword.
                    7. Display detailed book info by ID.
                    8. Add book.
                    9. Edit book.
                    10. Delete book.
                    0. Back.""");
            choice = IO.inputNumber();
            switch (choice) {
                case 1 -> showAllBooks();
                case 2 -> showAvailableBooks();
                case 3 -> findByAuthor();
                case 4 -> findByTitle();
                case 5 -> findByCategory();
                case 6 -> findByKeyword();
                case 7 -> showDetailedInfo();
                case 8 -> addBook();
                case 9 -> editBook();
                case 10 -> deleteBook();
                case 0 -> active = false;
            }
        }
    }

    public void showAllBooks(){
        ArrayList<BookListDTO> bookDTOs = bookListDTOService.getAllBooksList();
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void showAvailableBooks(){
        ArrayList<BookListDTO> bookDTOs = bookListDTOService.getAvailableBooksList();
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void findByAuthor(){
        ArrayList<BookListDTO> bookDTOs;
        System.out.println("Please enter author:");
        String searchTerm = scanner.nextLine().trim();
        if (IO.isNumeric(searchTerm)){
            bookDTOs = bookListDTOService.getBookListByAuthorId(Integer.parseInt(searchTerm));
        } else {
            bookDTOs = bookListDTOService.getBookListByAuthorName(searchTerm);
        }
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void findByTitle(){
        System.out.println("Please enter title:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookListDTO> bookDTOs = bookListDTOService.getBookListByTitle(searchTerm);
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void findByCategory(){
        ArrayList<BookListDTO> bookDTOs;
        System.out.println("Please enter category:");
        String searchTerm = scanner.nextLine();
        if (IO.isNumeric(searchTerm)){
            bookDTOs = bookListDTOService.getBookListByCategoryId(Integer.parseInt(searchTerm));
        } else {
            bookDTOs = bookListDTOService.getBookListByCategory(searchTerm);
        }
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void findByKeyword(){
        System.out.println("Please enter keyword:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookListDTO> bookDTOs = bookListDTOService.getBookListByKeyword(searchTerm);
        for (BookListDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    public void showDetailedInfo(){
        System.out.println("Please enter book ID:");
        int searchId = IO.inputNumber();
        Book book = bookService.getBookById(searchId);
        System.out.println(book.toString());
    }

    public void borrowBook(){
        System.out.println("Please enter book ID:");
        int bookId = Integer.parseInt(scanner.nextLine().trim());
        try {
            Book book = bookService.getBookById(bookId);
            loanService.createLoan(book, Main.loggedInUser);
        }
        catch (CantCreateLoanException e) {
            System.out.println("You can't borrow book "+ bookId + ".");
            System.out.println(e.getMessage());
        }
    }

    public void deleteBook(){
        System.out.println("Please enter book ID:");
        int bookId = IO.inputNumber();
        try {
            Book book = bookService.getBookById(bookId);
            bookService.remove(book);
        } catch (BookDoesNotExistException e) {
            System.out.println("There is no book with that ID.");
        } catch (CantRemoveBookException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addBook() {
        String title, isbn, summary, language;
        int yearPublished, copies, pageCount;
        ArrayList<Integer> authorIdList;
        ArrayList<Integer> categoryIdList;
        title = askForTitle();
        isbn = askForISBN();
        yearPublished = askForYearPublished();
        copies = askForCopies();
        summary = askForSummary();
        pageCount = askForPageCount();
        language = askForLanguage();

        authorIdList=createAuthorIdList();
        categoryIdList = createCategoryIdList();
        Book book = new Book(title, isbn, yearPublished, copies, copies, summary, pageCount, language);
        try {
            bookService.addBook(book, authorIdList, categoryIdList);
        } catch (CantCreateBookException e) {
            System.out.println(e.getMessage());
        }

    }

    public void editBook(){
        boolean active = true;
        String title, isbn, summary, language;
        int yearPublished, copies, pageCount;
        ArrayList<Author> authors;
        ArrayList<Category> categories;
        Book book = null;
        while (active) {
            System.out.println("Which book do you wish to edit?");
            int id = IO.inputNumber();
            if (bookService.exists(id)) {
                book = bookService.getBookById(id);
                active = false;
            } else {
                System.out.println("There is no book with that ID.");
            }
        }
        active = true;
        while (active) {
            System.out.println("What do you wish to edit?");
            System.out.println("1. Title: " + book.getTitle());
            System.out.println("2. ISBN: " + book.getIsbn());
            System.out.println("3. Year Published: " + book.getYearPublished());
            System.out.println("4. Number of copies: " + book.getTotalCopies());
            System.out.println("5. Summary: " + book.getSummary());
            System.out.println("6. Page count: " + book.getPageCount());
            System.out.println("7. Language: " + book.getLanguage());
            System.out.println("8. Authors: " +book.listAuthors() + " (this will save the changes immediately)");
            System.out.println("9. Categories: "+book.listCategories() + " (this will save the changes immediately)");
            System.out.println("10. Save and exit");
            System.out.println("0. Exit without saving.");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 ->{
                    title = askForTitle();
                    book.setTitle(title);
                }
                case 2 ->{
                    isbn = askForISBN();
                    book.setIsbn(isbn);
                }
                case 3 ->{
                    yearPublished = askForYearPublished();
                    book.setYearPublished(yearPublished);
                }
                case 4 ->{
                    copies = askForCopies();
                    int copiesChange = book.getTotalCopies()-copies;
                    book.setTotalCopies(copies);
                    //Change available copies by the same amount as total copies, but with a minimum of 0.
                    book.setAvailableCopies(Math.max(0, book.getAvailableCopies()+copiesChange));
                }
                case 5 ->{
                    summary = askForSummary();
                    book.setSummary(summary);
                }
                case 6 ->{
                    pageCount = askForPageCount();
                    book.setPageCount(pageCount);
                }
                case 7 ->{
                    language = askForLanguage();
                    book.setLanguage(language);
                }
                case 8 -> {
                    authors = askForAuthors(book.getAuthors());
                    book.setAuthors(authors);
                }
                case 9 -> {
                    categories = askForCategories(book.getCategories());
                    book.setCategories(categories);
                }
                case 10 ->{
                    bookService.save(book);
                    bookService.saveAuthors(book);
                    bookService.saveCategories(book);
                    active = false;
                }
                case 0 -> active = false;
            }
        }
    }

    public ArrayList<Author> askForAuthors(ArrayList<Author> authors){
        boolean active = true;
        ArrayList<Author> newAuthors = (ArrayList<Author>) authors.clone();
        while(active){
            if(newAuthors.isEmpty()) {
                System.out.println("The book does not currently have any authors.");
            } else {
                System.out.println("Current Authors:");
                for (Author author : newAuthors) {
                    System.out.println(author.getId() + " | " + author.getFullName());
                }
            }
            System.out.println("""
                1. Add author by author ID.
                2. Remove author by author ID.
                3. Display all authors.
                4. Find authors by partial name.
                0. Finish editing authors.""");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 -> {
                    System.out.println("Please enter author ID:");
                    int id = IO.inputNumber();
                    if (authorService.exists(id)) {
                        if(!existsInAuthorList(newAuthors,id)){
                            newAuthors.add(authorService.getAuthorById(id));
                        } else {
                            System.out.println("That author is already in the list.");
                        }
                    } else {
                        System.out.println("That author ID does not exist.");
                    }
                }

                case 2 -> {
                    System.out.println("Please enter author ID:");
                    int id = IO.inputNumber();
                    if (authorService.exists(id)) {
                        removeFromAuthorList(newAuthors, id);
                    } else {
                        System.out.println("That author ID does not exist.");
                    }
                }

                case 3 -> {
                    ArrayList<AuthorListDTO> authorlist = authorService.getAllAuthorListDTOs();
                    System.out.println("ID | Name");
                    for (AuthorListDTO author : authorlist) {
                        System.out.println(author.toString());
                    }
                }
                case 4 -> {
                    System.out.println("Please enter partial name:");
                    String name = scanner.nextLine().trim();
                    ArrayList<AuthorListDTO> authorlist = authorService.getAuthorListDTOsByPartialName(name);
                    if(authorlist.isEmpty()){
                        System.out.println("There are no authors with that name.");
                    } else {
                        System.out.println("ID | Name");
                        for (AuthorListDTO author : authorlist) {
                            System.out.println(author.toString());
                        }
                    }
                }
                case 0 -> active=false;
                case default -> System.out.println("Please enter a valid option.");
            }
        }
        return newAuthors;
    }

    public ArrayList<Category> askForCategories(ArrayList<Category> categories){
        boolean active = true;
        ArrayList<Category> newCategories = (ArrayList<Category>) categories.clone();
        while(active){
            if(newCategories.isEmpty()) {
                System.out.println("The book does not currently have any categories.");
            } else {
                System.out.println("Current Categories:");
                for (Category category : newCategories) {
                    System.out.println(category.getId() + " | " + category.getName());
                }
            }
            System.out.println("""
                1. Add category by category ID.
                2. Remove category by category ID.
                3. Display all categories.
                4. Find categories by partial name.
                0. Finish editing categories.""");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 -> {
                    System.out.println("Please enter category ID:");
                    int id = IO.inputNumber();
                    if (categoryService.exists(id)) {
                        if(!existsInCategoryList(newCategories,id)){
                            newCategories.add(categoryService.getCategoryById(id));
                        } else {
                            System.out.println("That category is already in the list.");
                        }
                    } else {
                        System.out.println("That category ID does not exist.");
                    }
                }

                case 2 -> {
                    System.out.println("Please enter category ID:");
                    int id = IO.inputNumber();
                    if (categoryService.exists(id)) {
                        removeFromCategoryList(newCategories, id);
                    } else {
                        System.out.println("That category ID does not exist.");
                    }
                }

                case 3 -> {
                    ArrayList<Category> categorylist = categoryService.getAllCategories();
                    System.out.println("ID | Name");
                    for (Category category : categorylist) {
                        System.out.println(category.getId() + " | " + category.getName());
                    }
                }
                case 4 -> {
                    System.out.println("Please enter partial name:");
                    String name = scanner.nextLine().trim();
                    ArrayList<Category> categorylist = categoryService.getCategoriesByPartialName(name);
                    if(categories.isEmpty()){
                        System.out.println("There are no categories with that name.");
                    } else {
                        System.out.println("ID | Name");
                        for (Category category : categorylist) {
                            System.out.println(category.getId() + " | " + category.getName());
                        }
                    }
                }
                case 0 -> active=false;
                case default -> System.out.println("Please enter a valid option.");
            }
        }
        return newCategories;
    }

    public ArrayList<Integer> createAuthorIdList() {
        boolean active=true;
        ArrayList<Integer> authorIdList = new ArrayList<>();
        while (active){
            System.out.println("""
                    Author list menu
                    1. Add author by author ID.
                    2. Display author list.
                    3. Find authors by partial name.
                    0. Finish adding authors.""");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 -> {
                    int id = IO.inputNumber();
                    if (authorService.exists(id)) {
                        authorIdList.add(id);
                    } else {
                        System.out.println("That author ID does not exist.");
                    }
                }
                case 2 -> {
                    ArrayList<AuthorListDTO> authors = authorService.getAllAuthorListDTOs();
                    System.out.println("ID | Name");
                    for (AuthorListDTO author : authors) {
                        System.out.println(author.toString());
                    }
                }
                case 3 -> {
                    System.out.println("Please enter partial name:");
                    String name = scanner.nextLine().trim();
                    ArrayList<AuthorListDTO> authors = authorService.getAuthorListDTOsByPartialName(name);
                    if(authors.isEmpty()){
                        System.out.println("There are no authors with that name.");
                    } else {
                        System.out.println("ID | Name");
                        for (AuthorListDTO author : authors) {
                            System.out.println(author.toString());
                        }
                    }
                }
                case 0 -> active=false;
                case default -> System.out.println("Please enter a valid option.");
            }
        }
        return authorIdList;
    }

    public ArrayList<Integer> createCategoryIdList() {
        boolean active=true;
        ArrayList<Integer> categoryIdList = new ArrayList<>();
        while (active){
            System.out.println("""
                    Author list menu
                    1. Add category by category ID.
                    2. Display category list.
                    3. Find categories by partial name.
                    0. Finish adding categories.""");
            int choice = IO.inputNumber();
            switch (choice){
                case 1 -> {
                    int id = IO.inputNumber();
                    if (categoryService.exists(id)) {
                        categoryIdList.add(id);
                    } else {
                        System.out.println("That category ID does not exist.");
                    }
                }
                case 2 -> {
                    ArrayList<Category> categories = categoryService.getAllCategories();
                    System.out.println("ID | Category | Description");
                    for (Category category : categories) {
                        System.out.println(category.toString());
                    }
                }
                case 3 -> {
                    System.out.println("Please enter partial name:");
                    String name = scanner.nextLine().trim();
                    ArrayList<Category> categories = categoryService.getCategoriesByPartialName(name);
                    if(categories.isEmpty()){
                        System.out.println("There are no categories with that name.");
                    } else {
                        System.out.println("ID | Category | Description");
                        for (Category category : categories) {
                            System.out.println(category.toString());
                        }
                    }
                }
                case 0 -> active=false;
                case default -> System.out.println("Please enter a valid option.");
            }
        }
        return categoryIdList;
    }

    public String askForTitle(){
        System.out.println("Please enter book title:");
        String title = scanner.nextLine().trim();
        return title;
    }

    public String askForISBN(){
        System.out.println("Please enter ISBN:");
        String isbn = scanner.nextLine().trim();
        // I choose not to validate ISBN because doing that properly would add a lot of work to testing that's beyond the scope of the project.
        return isbn;
    }

    public int askForYearPublished(){
        boolean active = true;
        int yearPublished = 0;
        while(active){
            System.out.println("Please enter publication year:");
            yearPublished = IO.inputNumber();
            if(yearPublished> LocalDate.now().getYear()) {
                System.out.println("The publication year must be this year or earlier.");
            } else {
                active = false;
            }
        }
        return yearPublished;
    }

    public int askForCopies(){
        boolean active = true;
        int copies =0;
        while (active){
            System.out.println("Please enter how many copies the library has:");
            copies = IO.inputNumber();
            if(copies<0) {
                System.out.println("The number of copies can't be negative.");
            } else {
                active = false;
            }
        }
        return copies;
    }

    public String askForSummary(){
        System.out.println("Please enter a summary of the book:");
        String summary = scanner.nextLine().trim();
        return summary;
    }

    public int askForPageCount(){
        boolean active = true;
        int pageCount =0;
        while (active){
            System.out.println("Please enter how many pages the book has:");
            pageCount = IO.inputNumber();
            if(pageCount<0) {
                System.out.println("Page count can't be negative.");
            } else {
                active = false;
            }
        }
        return pageCount;
    }

    public String askForLanguage(){
        boolean active = true;
        String language ="";
        while(active) {
            System.out.println("Please enter the book's language:");
            language = scanner.nextLine().trim();
            if(language.length()<2){
                System.out.println("That is too short.");
            } else {
                language = language.substring(0,1).toUpperCase()+language.substring(1).toLowerCase();
                active=false;
            }
        }
        return language;
    }

    public boolean existsInAuthorList(ArrayList<Author> authors, int authorId){
        for(Author author : authors){
            if(author.getId()==authorId) return true;
        }
        return false;
    }

    public boolean existsInCategoryList(ArrayList<Category> categories, int categoryId){
        for(Category category : categories){
            if(category.getId() == categoryId) return true;
        }
        return false;
    }

    public void removeFromAuthorList(ArrayList<Author> authors, int authorId){
        for(Author author : authors){
            if(author.getId()==authorId) {
                authors.remove(author);
                return;
            }
        }
        System.out.println("Author ID " + authorId + " not found in list.");
    }

    public void removeFromCategoryList(ArrayList<Category> categories, int categoryId) {
        for(Category category : categories){
            if(category.getId()==categoryId){
                categories.remove(category);
                return;
            }
        }
        System.out.println("Category ID " + categoryId + " not found in list.");
    }

}
