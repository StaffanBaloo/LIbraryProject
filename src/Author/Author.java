package Author;

import java.time.LocalDate;
import java.time.Period;

public class Author {
    private int id;
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
    private AuthorDescription authorDescription;

    public Author(LocalDate birthDate, String nationality, String lastName, String firstName, int id, AuthorDescription authorDescription) {
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.authorDescription = authorDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public AuthorDescription getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(AuthorDescription authorDescription) {
        this.authorDescription = authorDescription;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public int getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
