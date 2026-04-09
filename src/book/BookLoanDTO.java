package book;

public class BookLoanDTO {
    private int bookId;
    private String title;

    public BookLoanDTO(int bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static BookLoanDTO mapToBookLoanDTO(Book book) {
        return new BookLoanDTO(book.getBookId(), book.getTitle());
    }
}
