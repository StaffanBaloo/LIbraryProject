package Author;

public class AuthorDescription {
    String biography;
    String website;

    public AuthorDescription(String biography, String website) {
        this.biography = biography;
        this.website = website;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
