package Book;

import java.util.ArrayList;

public class Book {

    private int bookId;
    private String title;
    private int yearPublished;
    private int availableCopies;
    private ArrayList<String> authorList;
    private String summary;
    private int pageCount;
    private String language;

    public Book(int bookId, String title, int yearPublished, int availableCopies, ArrayList<String> authorList, String summary, int pageCount, String language) {
        this.bookId = bookId;
        this.title = title;
        this.yearPublished = yearPublished;
        this.availableCopies = availableCopies;
        this.authorList = authorList;
        this.summary = summary;
        this.pageCount = pageCount;
        this.language = language;
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
        String fullBook = "";
        fullBook += "title = '" + title + "'";
        fullBook += " | yearPublished = " + yearPublished;
        fullBook += " | availableCopies = " + availableCopies;
        fullBook += " | author(s) = " + authors();
        fullBook += " | summary = '" + summary + "'";
        fullBook += " | pageCount=" + pageCount;
        fullBook += " | language='" + language;
        return fullBook;
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

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public ArrayList<String> getAuthors() {
        return authorList;
    }

    public void addAuthor(String author) {
        this.authorList.add(author);
    }

    public void clearAuthors(){
        authorList.clear();
    }

    public void setSingleAuthor (String author) {
        authorList.clear();
        authorList.add(author);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
