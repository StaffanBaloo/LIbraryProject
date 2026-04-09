package author;

import java.util.ArrayList;

public class AuthorService {

    AuthorRepository authorRepository = new AuthorRepository();

    public ArrayList<Author> getThinAuthorsByBookId(int bookId){
        return authorRepository.getAuthorsByBookId();
    }

}
