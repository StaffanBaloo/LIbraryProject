package book;

import mapper.BookMapper;

import java.util.ArrayList;
import java.util.Optional;

public class BookListDTOService {
    BookService bookService = new BookService();



    public ArrayList<BookListDTO> getAllBooksList(){
        return BookMapper.mapToBookListDTOs(bookService.getAllBooks());
    }

    public ArrayList<BookListDTO> getBookListByTitle(String searchTerm){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByTitle(searchTerm));
    }

    public ArrayList<BookListDTO> getAvailableBooksList(){
        return BookMapper.mapToBookListDTOs(bookService.getAvailableBooks());
    }

    public ArrayList<BookListDTO> getBookListByAuthorId(int authorId){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByAuthorId(authorId));
    }

    public ArrayList<BookListDTO> getBookListByAuthorName(String searchTerm){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByAuthorName(searchTerm));
    }

    public ArrayList<BookListDTO> getBookListByCategoryId(int categoryId){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByCategoryId(categoryId));
    }

    public ArrayList<BookListDTO> getBookListByCategory(String searchTerm){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByCategory(searchTerm));
    }

    public ArrayList<BookListDTO> getBookListByKeyword(String searchTerm){
        return BookMapper.mapToBookListDTOs(bookService.getBooksByKeyword(searchTerm));
    }

    public Optional<BookListDTO> getBookById(int bookId) {
        var maybeBook = bookService.getBookById(bookId);
        if(maybeBook.isPresent()) {
            return Optional.of(BookMapper.mapToBookListDTO(maybeBook.get()));
        } else {
            return Optional.empty();
        }
    }

}
