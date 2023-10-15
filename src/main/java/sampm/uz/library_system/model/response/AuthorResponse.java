package sampm.uz.library_system.model.response;

import jakarta.persistence.OneToMany;
import lombok.Data;
import sampm.uz.library_system.entity.Book;

import java.util.List;

@Data
public class AuthorResponse {

    private String fullName;

    private String email;

    private String city;

//    private List<Book> bookList;
}
