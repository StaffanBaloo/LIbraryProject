package book;

import java.util.ArrayList;

public class BookListDTO {

    private int bookId;
    private String title;
    private ArrayList<String> authorList;
    private int availableCopies;

    public BookListDTO(int bookId, String title, ArrayList<String> authorList, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.authorList = authorList;
        this.availableCopies = availableCopies;
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

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public ArrayList<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(ArrayList<String> authorList) {
        this.authorList = authorList;
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


}
