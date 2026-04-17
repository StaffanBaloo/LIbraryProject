package book;

import author.*;
import category.*;
import exceptions.*;
import loan.LoanRepository;

import java.util.ArrayList;

public class BookService {

    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    LoanRepository loanRepository = new LoanRepository();



    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> books = bookRepository.getAllBooks();
        for(Book book : books){
            book.setAuthors(getAuthorsByBookId(book.getBookId()));
            book.setCategories(getCategoriesByBookId(book.getBookId()));
        }
        return books;
    }

    public ArrayList<Author> getAuthorsByBookId(int bookId){
        return authorRepository.getAuthorsByBookId(bookId);
    }

    public ArrayList<Category> getCategoriesByBookId(int bookId){
        return categoryRepository.getCategoriesByBookId(bookId);
    }

    public ArrayList<Book> getBooksByTitle(String searchTerm){
        return bookRepository.getBooksByTitle(searchTerm);
    }

    public ArrayList<Book> getAvailableBooks(){
        ArrayList<Book> books = bookRepository.getAllBooks();
        ArrayList<Book> availableBooks = new ArrayList<>();
        for(Book book: books) {
            if(book.getAvailableCopies()>0){
                fillWithAuthorsAndCategories(book);
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public void fillWithAuthorsAndCategories (Book book) {
        book.setAuthors(getAuthorsByBookId(book.getBookId()));
        book.setCategories(getCategoriesByBookId(book.getBookId()));
    }

    public void fillListWithAuthorsAndCategories (ArrayList<Book> books) {
        for (Book book : books){
            fillWithAuthorsAndCategories(book);
        }
    }

    public boolean exists(int bookId) {
        return bookRepository.exists(bookId);
    }

    public ArrayList<Book> getBooksByAuthorId(int authorId) {
        ArrayList<Book> bookList = bookRepository.getBooksByAuthorId(authorId);
        fillListWithAuthorsAndCategories(bookList);
        return bookList;
    }

    public ArrayList<Book> getBooksByAuthorName(String searchTerm) {
        ArrayList<Book> bookList = bookRepository.getBooksByAuthorName(searchTerm);
        fillListWithAuthorsAndCategories(bookList);
        return bookList;
    }

    public ArrayList<Book> getBooksByCategoryId(int categoryId) {
        ArrayList<Book> bookList = bookRepository.getBooksByCategoryId(categoryId);
        fillListWithAuthorsAndCategories(bookList);
        return bookList;
    }

    public ArrayList<Book> getBooksByCategory(String searchTerm) {
        ArrayList<Book> bookList = bookRepository.getBooksByCategory(searchTerm);
        fillListWithAuthorsAndCategories(bookList);
        return bookList;
    }

    public ArrayList<Book> getBooksByKeyword(String searchTerm) {
        ArrayList<Book> bookList = bookRepository.getBooksByKeyword(searchTerm);
        fillListWithAuthorsAndCategories(bookList);
        return bookList;
    }

    public void remove (Book book) {
        if(loanRepository.getNumberOfCurrentLoansByBook(book)==0) {
            book.setTotalCopies(0);
            book.setAvailableCopies(0);
            bookRepository.save(book);
        } else {
            throw new CantRemoveBookException ("There are active loans for book "+book.getBookId()+".");
        }
    }

    public void addBook(Book book, ArrayList<Integer> authorIdList, ArrayList<Integer> categoryIdList) {
        for(int authorId : authorIdList) {
            if(!authorRepository.exists(authorId)) {
                throw new CantCreateBookException("Author ID "+authorId+" does not exist.");
            }
        }
        for(int categoryId : categoryIdList) {
            if(!categoryRepository.exists(categoryId)) {
                throw new CantCreateBookException("Category ID "+categoryId+" does not exist.");
            }
        }
        bookRepository.addBook(book, authorIdList, categoryIdList);
    }

    public void save(Book book){
        bookRepository.save(book);
    }

    public void saveAuthors(Book book) {
        bookRepository.saveAuthors(book);
    }

    public void saveCategories(Book book) {
        bookRepository.saveCategories(book);
    }

    public Book getBookById(int bookId) {
        Book book;
        if(bookRepository.exists(bookId)) {
            book = bookRepository.getBookById(bookId);
            fillWithAuthorsAndCategories(book);
        } else {
            throw new BookDoesNotExistException ("Book "+bookId+ " does not exist.");
        }
        return book;
    }


}
