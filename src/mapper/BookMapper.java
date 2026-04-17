package mapper;

import author.Author;
import book.Book;
import book.BookListDTO;

import java.util.ArrayList;

public class BookMapper {

    public static BookListDTO mapToDTO(Book book) {
        ArrayList<String> authorList = new ArrayList<>();
        for(Author author: book.getAuthors()) {
            authorList.add(author.getFullName());
        }
        return new BookListDTO(book.getBookId(), book.getTitle(), authorList, book.getAvailableCopies());
    }

    public static ArrayList<BookListDTO> mapToDTOs(ArrayList<Book> books) {
        ArrayList<BookListDTO> bookDTOs =new ArrayList<>();
        for(Book book: books){
            bookDTOs.add(mapToDTO(book));
        }
        return bookDTOs;
    }
}
