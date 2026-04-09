package book;

import author.*;
import category.*;

import java.util.ArrayList;

public class BookService {

    BookRepository bookRepository = new BookRepository();
    AuthorRepository authorRepository = new AuthorRepository();
    CategoryRepository categoryRepository = new CategoryRepository();



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
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public ArrayList<Book> getBooksByAuthorId(int authorId) {
        return bookRepository.getBooksByAuthorId(authorId);
    }

    public ArrayList<Book> getBooksByAuthorName(String searchTerm) {
        return bookRepository.getBooksByAuthorName(searchTerm);
    }

    public ArrayList<Book> getBooksByCategoryId(int categoryId) {
        return bookRepository.getBooksByCategoryId(categoryId);
    }

    public ArrayList<Book> getBooksByCategory(String searchTerm) {
        return bookRepository.getBooksByCategory(searchTerm);
    }

    public ArrayList<Book> getBooksByKeyword(String searchTerm) {
        return bookRepository.getBooksByKeyword(searchTerm);
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }


}
