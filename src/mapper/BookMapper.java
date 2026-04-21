package mapper;

import author.Author;
import book.Book;
import book.BookListDTO;
import book.BookLoanDTO;

import java.util.ArrayList;

public class BookMapper {

    public static BookListDTO mapToBookListDTO(Book book) {
        ArrayList<String> authorList = new ArrayList<>();
        for(Author author: book.getAuthors()) {
            authorList.add(author.getFullName());
        }
        return new BookListDTO(book.getBookId(), book.getTitle(), authorList, book.getAvailableCopies());
    }

    public static ArrayList<BookListDTO> mapToBookListDTOs(ArrayList<Book> books) {
        ArrayList<BookListDTO> bookDTOs =new ArrayList<>();
        for(Book book: books){
            bookDTOs.add(mapToBookListDTO(book));
        }
        return bookDTOs;
    }

    public static BookLoanDTO maptoBookLoanDTO(Book book) {
        return new BookLoanDTO(book.getBookId(), book.getTitle(), book.getAvailableCopies());
    }
}
