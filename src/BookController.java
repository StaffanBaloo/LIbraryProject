import java.util.ArrayList;
import java.util.Scanner;

public class BookController {
    BookService bookService = new BookService();
    LoanService loanService = new LoanService();
    Scanner scanner = new Scanner(System.in);
    int userId;

    public BookController(int userId) {
        this.userId = userId;
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
                case 1: {
                    showAllBooks();
                    break;
                }
                case 2: {
                    showAvailableBooks();
                    break;
                }
                case 3: {
                    findByAuthor();
                    break;
                }
                case 4: {
                    findByTitle();
                    break;
                }
                case 5: {
                    findByCategory();
                    break;
                }
                case 6: {
                    findByKeyword();
                    break;
                }
                case 7:{
                    showDetailedInfo();
                    break;
                }
                case 8: {
                    borrowBook();
                    break;

                }
                case 0: {
                    active = false;
                    break;
                }

            }
        }
    }
    void showAllBooks(){
        ArrayList<BookDTO> bookDTOs = bookDTOService.getAllBookDTOs();
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void showAvailableBooks(){
        ArrayList<BookDTO> bookDTOs = bookDTOService.getAllAvailableBookDTOs();
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void findByAuthor(){
        System.out.println("Please enter author:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookDTO> bookDTOs = bookDTOService.getBooksByAuthor(searchTerm);
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void findByTitle(){
        System.out.println("Please enter title:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookDTO> bookDTOs = bookDTOService.getBooksByTitle(searchTerm);
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void findByCategory(){
        System.out.println("Please enter category:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookDTO> bookDTOs = bookDTOService.getBooksByCategory(searchTerm);
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void findByKeyword(){
        System.out.println("Please enter keyword:");
        String searchTerm = scanner.nextLine();
        ArrayList<BookDTO> bookDTOs = bookDTOService.getBooksByKeyword(searchTerm);
        for (BookDTO bookDTO : bookDTOs) {
            System.out.println(bookDTO.toString());
        }
    }

    void showDetailedInfo(){
        System.out.println("Please enter book ID:");
        int searchId = Integer.parseInt(scanner.nextLine());
        Book book = bookService.getBookById(searchId);
        System.out.println(book.toString());
    }

    void borrowBook(){
        System.out.println("Please enter book ID:");
        int bookId = Integer.parseInt(scanner.nextLine());
        try {
            loanService.createLoan(bookId, userId);
        }
        catch (CantCreateLoanException e) {
            System.out.println("You can't borrow book "+ bookId + ".");
            System.out.println(e.getMessage());
        }
    }
}
