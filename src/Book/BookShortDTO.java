package Book;

import java.util.ArrayList;

public class BookShortDTO {

    private int bookId;
    private String title;
    private ArrayList<String> authorList;
    private int availableCopies;

    public BookShortDTO(int bookId, String title, ArrayList<String> authorList, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.authorList = authorList;
        this.availableCopies = availableCopies;
    }

    public String authors(){
        String tempAuthors="";
        for (int i = 0; i<authorList.size(); i++) {
            if (i==0) {
                tempAuthors+=authorList.get(i);
            }
            else if(i==authorList.size()-1) {
                tempAuthors += " and " + authorList.get(i);
            }
            else {
                tempAuthors += ", " +authorList.get(i);
            }
        }
        return tempAuthors;
    }

    @Override
    public String toString() {
        String fullBook = Integer.toString(bookId);
        fullBook += " | " + title;
        fullBook += " | " + authors();
        fullBook += " | " + availableCopies;
        return fullBook;
    }

    public static BookShortDTO mapToDTO(Book book) {
        return new BookShortDTO(book.getBookId(), book.getTitle(), book.getAuthors(), book.getAvailableCopies());
    }
}
