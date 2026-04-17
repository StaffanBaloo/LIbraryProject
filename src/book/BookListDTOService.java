package book;

import mapper.BookMapper;

import java.util.ArrayList;

public class BookListDTOService {
    BookService bookService = new BookService();



    public ArrayList<BookListDTO> getAllBooksList(){
        return BookMapper.mapToDTOs(bookService.getAllBooks());
    }

    public ArrayList<BookListDTO> getBookListByTitle(String searchTerm){
        return BookMapper.mapToDTOs(bookService.getBooksByTitle(searchTerm));
    }

    public ArrayList<BookListDTO> getAvailableBooksList(){
        return BookMapper.mapToDTOs(bookService.getAvailableBooks());
    }

    public ArrayList<BookListDTO> getBookListByAuthorId(int authorId){
        return BookMapper.mapToDTOs(bookService.getBooksByAuthorId(authorId));
    }

    public ArrayList<BookListDTO> getBookListByAuthorName(String searchTerm){
        return BookMapper.mapToDTOs(bookService.getBooksByAuthorName(searchTerm));
    }

    public ArrayList<BookListDTO> getBookListByCategoryId(int categoryId){
        return BookMapper.mapToDTOs(bookService.getBooksByCategoryId(categoryId));
    }

    public ArrayList<BookListDTO> getBookListByCategory(String searchTerm){
        return BookMapper.mapToDTOs(bookService.getBooksByCategory(searchTerm));
    }

    public ArrayList<BookListDTO> getBookListByKeyword(String searchTerm){
        return BookMapper.mapToDTOs(bookService.getBooksByKeyword(searchTerm));
    }

    public BookListDTO getBookById(int bookId) {
        return BookMapper.mapToDTO(bookService.getBookById(bookId));
    }

}
