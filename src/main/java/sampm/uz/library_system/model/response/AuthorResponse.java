package sampm.uz.library_system.model.response;

import lombok.Data;

@Data
public class AuthorResponse {

    private String fullName;

    private String email;

    private String city;

//    private List<Book> bookList;
}
