package book;

import java.util.ArrayList;

public class BookShortDTOService {
    BookService bookService = new BookService();

    ArrayList<BookShortDTO> mapToDTOs(ArrayList<Book> books) {
        ArrayList<BookShortDTO> bookDTOs =new ArrayList<>();
        for(Book book: books){
            bookDTOs.add(BookShortDTO.mapToDTO(book));
        }
        return bookDTOs;
    }

    public ArrayList<BookShortDTO> getAllBookDTOs(){
        return mapToDTOs(bookService.getAllBooks());
    }

    public ArrayList<BookShortDTO> getBookDTOsByTitle(String searchTerm){
        return mapToDTOs(bookService.getBooksByTitle(searchTerm));
    }

    public ArrayList<BookShortDTO> getAvailableBookDTOs(){
        return mapToDTOs(bookService.getAvailableBooks());
    }

    public ArrayList<BookShortDTO> getBooksByAuthorId(int authorId){
        return mapToDTOs(bookService.getBooksByAuthorId(authorId);
    }

    public ArrayList<BookShortDTO> getBooksByAuthorName(String searchTerm){
        return mapToDTOs(bookService.getBooksByAuthorName(searchTerm);
    }

    public ArrayList<BookShortDTO> getBooksByCategoryId(int categoryId){
        return mapToDTOs(bookService.getBooksByCategoryId(categoryId);
    }

    public ArrayList<BookShortDTO> getBooksByCategory(String searchTerm){
        return mapToDTOs(bookService.getBooksByCategory(searchTerm);
    }

    public ArrayList<BookShortDTO> getBooksByKeyword(String searchTerm){
        return mapToDTOs(bookService.getBooksByKeyword(searchTerm);
    }

    public BookShortDTO getBookById(int bookId) {
        return BookShortDTO.mapToDTO(bookService.getBookById(bookId));
    }

}
