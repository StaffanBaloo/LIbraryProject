package author;

import IO;
import book.BookListDTO;
import exceptions.*;
import org.apache.commons.validator.routines.UrlValidator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthorController {
    AuthorService authorService = new AuthorService();
    Scanner scanner = new Scanner(System.in);

    public AuthorController() {

    }

    public void showLibrarianMenu() {
        boolean active = true;

        while (active) {
            System.out.println("""
                    Author menu
                    Do you want to:
                    1. Add an author?
                    2. Show all authors?
                    2. Search for author by name?
                    3. Search for author by nationality?
                    4. View author information?
                    5. Find books by an author?
                    6. Edit an author?
                    7. Remove an author?
                    0. Return?""");
            int choice = IO.inputNumber();
            switch (choice) {
                case 1 -> addAuthor();
                case 2 -> showAuthors();
                case 3 -> findByName();
                case 4 -> findByNationality();
                case 5 -> displayAuthor();
                case 6 -> findBooksByAuthor();
                case 7 -> editAuthor();
                case 8 -> deleteAuthor();
                case 0 -> active = false;
                default -> System.out.println("Please choose a valid option");
            }
        }
    }

    public void addAuthor() {
        String firstName = askForFirstName();
        String lastName = askForLastName();
        String nationality = askForNationality();
        LocalDate birthdate = askForBirthDate();
        String biography = askForBiography();
        String website = askForWebsite();
        Author author = new Author(firstName, lastName, nationality, birthdate, biography, website);
        try {
            authorService.addAuthor(author);
            System.out.println("Author "+author.getFullName()+" created.");
        } catch (CantCreateAuthorException e) {
            System.out.println("Could not create author "+author.getFullName()+".");
        }
    }

    public void editAuthor(){
        boolean active = true;
        while(active){
            System.out.println("Please enter author ID (0 to go back):");
            int id = IO.inputNumber();
            if(id==0){
                active = false;
            } else if(id<1){
                System.out.println("ID has to be a positive number.");
            } else if(!authorService.exists(id)) {
                System.out.println("No author with that ID exists.");
            } else {
                Author author = authorService.getAuthorById(id);
                boolean active2 = true;
                while(active2) {
                    System.out.println("What information do you want to edit?");
                    System.out.println("1. First name: " + author.getFirstName());
                    System.out.println("2. Last name: " + author.getLastName());
                    System.out.println("3. Nationality: " + author.getNationality());
                    System.out.println("4. Birthdate: " + author.getBirthDate());
                    System.out.println("5. Biography: " + author.getBiography());
                    System.out.println("6. Website: " + author.getWebsite());
                    System.out.println("9. Exit and save.");
                    System.out.println("0. Exit without saving.");
                    int choice = IO.inputNumber();
                    switch (choice){
                        case 1 -> author.setFirstName(askForFirstName());
                        case 2 -> author.setLastName(askForLastName());
                        case 3 -> author.setNationality(askForNationality());
                        case 4 -> author.setBirthDate(askForBirthDate());
                        case 5 -> author.setBiography(askForBiography());
                        case 6 -> author.setWebsite(askForWebsite());
                        case 9 -> {
                            try {
                                authorService.save(author);
                                active2 = false;
                                active = false;
                                System.out.println("Author " + author.getFullName() + " saved.");
                            } catch (CantSaveAuthorException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 0 -> {
                            active2 = false;
                            active = false;
                        }
                    }
                }
            }
        }
    }

    public void displayAuthor(){
        boolean active = true;
        while(active){
            System.out.println("Please enter author ID (0 to go back):");
            int id = IO.inputNumber();
            if(id==0){
                active = false;
            } else if(id<1){
                System.out.println("ID has to be a positive number.");
            } else if(!authorService.exists(id)) {
                System.out.println("No author with that ID exists.");
            } else {
                active = false;
                Author author = authorService.getAuthorById(id);
                System.out.println("First name: " + author.getFirstName());
                System.out.println("Last name: " + author.getLastName());
                System.out.println("Nationality: " + author.getNationality());
                System.out.println("Birthdate: " + author.getBirthDate());
                System.out.println("Biography: " + author.getBiography());
                System.out.println("Website: " + author.getWebsite());
            }
        }
    }

    public void showAuthors() {
        ArrayList<AuthorListDTO> authors = authorService.getAllAuthorListDTOs();
        System.out.println("ID | Name");
        for (AuthorListDTO author : authors) {
            System.out.println(author.getId() + " | " + author.getFullName());
        }
    }

    public void findByName(){
        boolean active = true;
        while (active) {
            System.out.println("Please enter part of the author's name:");
            String name = scanner.nextLine().trim();
            if(name=="") {
                System.out.println("Name can't be empty.");
            } else {
                active = false;
                ArrayList<AuthorListDTO> authors = authorService.getAuthorListDTOsByPartialName(name);
                if(authors.isEmpty()) {
                    System.out.println("ID | Name");
                    for (AuthorListDTO author : authors) {
                        System.out.println(author.getId() + " | " + author.getFullName());
                    }
                } else {
                    System.out.println("Could not find any matching authors.");
                }
            }
        }
    }

    public void findByNationality(){
        boolean active = true;
        while (active) {
            System.out.println("Please enter part of the author's nationality:");
            String nationality = scanner.nextLine().trim();
            if(nationality=="") {
                System.out.println("Nationality can't be empty.");
            } else {
                active = false;
                ArrayList<AuthorListDTO> authors = authorService.getAuthorListDTOsByPartialNationality(nationality);
                if(authors.isEmpty() {
                    System.out.println("ID | Name");
                    for (AuthorListDTO author : authors) {
                        System.out.println(author.getId() + " | " + author.getFullName());
                    }
                } else {
                    System.out.println("Could not find any matching authors.");
                }
            }
        }
    }

    public void findBooksByAuthor(){
        boolean active = true;
        ArrayList<BookListDTO> books;
        while(active){
            System.out.println("Please enter author ID (or 0 to go back):");
            int id = IO.inputNumber();
            if(id==0) {
                active = false;
            }
            else if(authorService.exists(id)) {
                active = false;
                books = authorService.getBooksByAuthorId(id);
                if(!books.isEmpty()) {
                    System.out.println("ID | Title");
                    for (BookListDTO book : books){
                        System.out.println(book.getBookId() + " | " + book.getTitle());
                    }
                } else {
                    System.out.println("No books found by that author.");
                }
            } else {
                System.out.println("No author with that ID exists.");
            }
        }
    }

    public void deleteAuthor(){
        boolean active = true;
        while(active){
            System.out.println("Please enter author ID (0 to go back):");
            int id = IO.inputNumber();
            if(id==0){
                active = false;
            } else if(id<1){
                System.out.println("ID has to be a positive number.");
            } else if(!authorService.exists(id)) {
                System.out.println("No author with that ID exists.");
            } else {
                active = false;
                Author author = authorService.getAuthorById(id);
                System.out.println("First name: " + author.getFirstName());
                System.out.println("Last name: " + author.getLastName());
                System.out.println("Nationality: " + author.getNationality());
                System.out.println("Birthdate: " + author.getBirthDate());
                System.out.println("Biography: " + author.getBiography());
                System.out.println("Website: " + author.getWebsite());
                System.out.println("Enter \"DELETE\" in all caps to delete author.");
                String choice = scanner.nextLine().trim();
                if(choice=="DELETE") {
                    try {
                        authorService.delete(author);
                        System.out.println(author.getFullName() + " deleted.");
                    } catch (CantDeleteAuthorException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Author not deleted.");
                }
            }
        }
    }

    public String askForFirstName(){
        boolean active = true;
        String firstName = "";
        while (active) {
            System.out.println("Please enter the author's first name.");
            firstName = scanner.nextLine().trim();
            if(firstName.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty name.");
            }
        }
        return firstName;
    }

    public String askForLastName(){
        boolean active = true;
        String lastName = "";
        while (active) {
            System.out.println("Please enter the author's last name.");
            lastName = scanner.nextLine().trim();
            if(lastName.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty name.");
            }
        }
        return lastName;
    }

    public String askForNationality(){
        boolean active = true;
        String nationality = "";
        while (active) {
            System.out.println("Please enter the author's nationality.");
            nationality = scanner.nextLine().trim();
            if(nationality.length()>0) {
                active=false;
            } else {
                System.out.println("Can't accept an empty nationality.");
            }
        }
        return nationality;
    }

    public String askForBiography(){
        String biography;
        System.out.println("Please enter the author's biography.");
        biography = scanner.nextLine().trim();
        return biography;
    }

    public String askForWebsite(){
        UrlValidator urlValidator = new UrlValidator();
        boolean active = true;
        String website = "";
        while (active) {
            System.out.println("Please enter the author's website.");
            website = scanner.nextLine().trim();
            if(!urlValidator.isValid(website)) {
                System.out.println("Please enter a valid website.");
            } else {
                active = false;
            }
        }
        return website;
    }

    public LocalDate askForBirthDate(){
        boolean active = true;
        LocalDate birthdate;
        while(active) {
            System.out.println("Please enter the author's birthdate (YYYY-MM-DD):");
            String dateString = scanner.nextLine().trim();
            try {
                birthdate = LocalDate.parse(dateString);
                active = false;
            } catch (DateTimeParseException e) {
                System.out.println("Date not valid.");
            }
        }
        return birthdate;
    }

}