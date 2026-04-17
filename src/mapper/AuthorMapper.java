package mapper;
import author.*;


import java.util.ArrayList;

public class AuthorMapper {
    public static AuthorListDTO mapToListDTO(Author author) {
        return new AuthorListDTO(author.getId(), author.getFirstName(), author.getLastName());
    }

    public static ArrayList<AuthorListDTO> mapToListDTOs(ArrayList<Author> authors) {
        ArrayList<AuthorListDTO> authorDTOs =new ArrayList<>();
        for(Author author : authors){
            authorDTOs.add(mapToListDTO(author));
        }
        return authorDTOs;
    }
}
